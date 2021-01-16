package kieker.monitoring.probe.spring.executions;

import kieker.common.logging.Log;
import kieker.common.logging.LogFactory;
import kieker.common.record.controlflow.OperationExecutionRecord;
import kieker.monitoring.core.controller.IMonitoringController;
import kieker.monitoring.core.controller.MonitoringController;
import kieker.monitoring.core.registry.ControlFlowRegistry;
import kieker.monitoring.core.registry.SessionRegistry;
import kieker.monitoring.probe.IMonitoringProbe;
import kieker.monitoring.timer.ITimeSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;


public class OperationExecutionMethodInvocationBasedOnAOP implements IMonitoringProbe {
    private static final Log LOG = LogFactory.getLog(OperationExecutionMethodInvocationInterceptor.class);

    private static final SessionRegistry SESSION_REGISTRY = SessionRegistry.INSTANCE;
    private static final ControlFlowRegistry CF_REGISTRY = ControlFlowRegistry.INSTANCE;

    private final IMonitoringController monitoringCtrl;
    private final ITimeSource timeSource;
    private final String hostname;
    public OperationExecutionMethodInvocationBasedOnAOP(){
        this(MonitoringController.getInstance());
    }
    public OperationExecutionMethodInvocationBasedOnAOP(IMonitoringController monitoringCtrl) {
        this.monitoringCtrl = monitoringCtrl;
        this.timeSource = this.monitoringCtrl.getTimeSource();
        this.hostname = this.monitoringCtrl.getHostname();
    }
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Do Around");
        if (!this.monitoringCtrl.isMonitoringEnabled()) {
            return proceedingJoinPoint.proceed();
        }


        final String sessionId = SESSION_REGISTRY.recallThreadLocalSessionId();
        final int eoi; // this is executionOrderIndex-th execution in this trace
        final int ess; // this is the height in the dynamic call tree of this execution
        final boolean entrypoint;

        long traceId = CF_REGISTRY.recallThreadLocalTraceId(); // traceId, -1 if entry point
        System.out.println("get Trace id:"+traceId);
        if (traceId == -1) {
            entrypoint = true;
            traceId = CF_REGISTRY.getAndStoreUniqueThreadLocalTraceId();
            System.out.println("This is entry point, set Trace Id:"+traceId);
            CF_REGISTRY.storeThreadLocalEOI(0);
            CF_REGISTRY.storeThreadLocalESS(1); // next operation is ess + 1
            eoi = 0;
            ess = 0;
        } else {
            entrypoint = false;
            eoi = CF_REGISTRY.incrementAndRecallThreadLocalEOI(); // ess > 1
            ess = CF_REGISTRY.recallAndIncrementThreadLocalESS(); // ess >= 0
            if ((eoi == -1) || (ess == -1)) {
                LOG.error("eoi and/or ess have invalid values:" + " eoi == " + eoi + " ess == " + ess);
                this.monitoringCtrl.terminateMonitoring();
            }
        }
        final long tin = this.timeSource.getTime();
        final Object retval;
        Object thisObject = proceedingJoinPoint.getTarget();
       // final String clazz = thisObject.getClass().getName();
        final int objectId = System.identityHashCode(thisObject);
        final Signature signature = proceedingJoinPoint.getSignature();
        try {
            retval = proceedingJoinPoint.proceed();
        } finally {

            final long tout = this.timeSource.getTime();
            this.monitoringCtrl.newMonitoringRecord(
                    new OperationExecutionRecord(signature.getDeclaringTypeName()+"."+signature.getName()+"()", Integer.toString(objectId), traceId, tin, tout, this.hostname, eoi, ess));
            // cleanup
            if (entrypoint) {
                CF_REGISTRY.unsetThreadLocalTraceId();
                CF_REGISTRY.unsetThreadLocalEOI();
                CF_REGISTRY.unsetThreadLocalESS();
            } else {
                CF_REGISTRY.storeThreadLocalESS(ess); // next operation is ess
            }
        }
        return retval;

    }

}
