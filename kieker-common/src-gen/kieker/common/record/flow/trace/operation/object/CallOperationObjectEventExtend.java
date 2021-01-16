package kieker.common.record.flow.trace.operation.object;


import kieker.common.record.flow.ICallObjectRecordExtend;
import kieker.common.record.io.IValueDeserializer;
import kieker.common.record.io.IValueSerializer;
import kieker.common.util.registry.IRegistry;

import java.nio.BufferOverflowException;

/**
 * @author yukun zhang
 * API compatibility: Kieker 1.13.0
 *
 * @since 1.13
 */
public class CallOperationObjectEventExtend extends CallOperationObjectEvent implements ICallObjectRecordExtend {
    private static final long serialVersionUID = -4876224316055177634L;
    /** Descriptive definition of the serialization size of the record. */
    public static final int SIZE = TYPE_SIZE_LONG // IEventRecord.timestamp
            + TYPE_SIZE_LONG // ITraceRecord.traceId
            + TYPE_SIZE_INT // ITraceRecord.orderIndex
            + TYPE_SIZE_STRING // IOperationSignature.operationSignature
            + TYPE_SIZE_STRING // IClassSignature.classSignature
            + TYPE_SIZE_STRING // ICallRecord.calleeOperationSignature
            + TYPE_SIZE_STRING // ICallRecord.calleeClassSignature
            + TYPE_SIZE_INT // IObjectRecord.objectId
            + TYPE_SIZE_INT // ICallObjectRecord.calleeObjectId
            + TYPE_SIZE_INT // ICallObjectRecordExtend.eoi
            + TYPE_SIZE_INT //  ICallObjectRecordExtend.ess
            + TYPE_SIZE_LONG // ICallObjectRecordExtend.tin
            + TYPE_SIZE_LONG // ICallObjectRecordExtend.tout
            ;
    public static final Class<?>[] TYPES = {
            long.class, // IEventRecord.timestamp
            long.class, // ITraceRecord.traceId
            int.class, // ITraceRecord.orderIndex
            String.class, // IOperationSignature.operationSignature
            String.class, // IClassSignature.classSignature
            String.class, // ICallRecord.calleeOperationSignature
            String.class, // ICallRecord.calleeClassSignature
            int.class, // IObjectRecord.objectId
            int.class, // ICallObjectRecord.calleeObjectId
            int.class,// ICallObjectRecordExtend.eoi
            int.class,//  ICallObjectRecordExtend.ess
            long.class, // ICallObjectRecordExtend.tin
            long.class // ICallObjectRecordExtend.tout
    };

    public static final long NO_TIMESTAMP = -1L;
    public static final int NO_EOI_ESS = -1;

    /** property name array. */
    private static final String[] PROPERTY_NAMES = {
            "timestamp",
            "traceId",
            "orderIndex",
            "operationSignature",
            "classSignature",
            "calleeOperationSignature",
            "calleeClassSignature",
            "objectId",
            "calleeObjectId",
            "eoi",
            "ess",
            "tin",
            "tout",
    };

    /** property declarations. */
    private final int eoi;
    private final int ess;
    private final long tin;
    private final long tout;

    /**
     * Creates a new instance of this class using the given parameters.
     *
     * @param timestamp
     *            timestamp
     * @param traceId
     *            traceId
     * @param orderIndex
     *            orderIndex
     * @param operationSignature
     *            operationSignature
     * @param classSignature
     *            classSignature
     * @param calleeOperationSignature
     *            calleeOperationSignature
     * @param calleeClassSignature
     *            calleeClassSignature
     * @param objectId
     *            objectId
     * @param calleeObjectId
     *            calleeObjectId
     */
    public CallOperationObjectEventExtend(final long timestamp, final long traceId, final int orderIndex, final String operationSignature, final String classSignature, final String calleeOperationSignature, final String calleeClassSignature, final int objectId, final int calleeObjectId, final int eoi, final int ess, final long tin, final long tout) {
        super(timestamp, traceId, orderIndex, operationSignature, classSignature, calleeOperationSignature, calleeClassSignature,objectId,calleeObjectId);
        this.eoi = eoi;
        this.ess = ess;
        this.tin = tin;
        this.tout = tout;
    }

    /**
     * This constructor converts the given array into a record.
     * It is recommended to use the array which is the result of a call to {@link #toArray()}.
     *
     * @param values
     *            The values for the record.
     *
     * @deprecated since 1.13. Use {@link #CallOperationEventExtend(IValueDeserializer)} instead.
     */
    @Deprecated
    public CallOperationObjectEventExtend(final Object[] values) { // NOPMD (direct store of values)
        super(values, TYPES);
        this.eoi = (Integer) values[9];
        this.ess = (Integer) values[10];
        this.tin = (Long) values[11];
        this.tout = (Long) values[12];
    }

    /**
     * This constructor uses the given array to initialize the fields of this record.
     *
     * @param values
     *            The values for the record.
     * @param valueTypes
     *            The types of the elements in the first array.
     *
     * @deprecated since 1.13. Use {@link #CallOperationEventExtend(IValueDeserializer)} instead.
     */
    @Deprecated
    protected  CallOperationObjectEventExtend(final Object[] values, final Class<?>[] valueTypes) { // NOPMD (values stored directly)
        super(values, valueTypes);
        this.eoi = (Integer) values[9];
        this.ess = (Integer) values[10];
        this.tin = (Long) values[11];
        this.tout = (Long) values[12];
    }

    /**
     * @param deserializer
     *            The deserializer to use
     */
    public CallOperationObjectEventExtend(final IValueDeserializer deserializer) {
        super(deserializer);
        this.eoi = deserializer.getInt();
        this.ess = deserializer.getInt();
        this.tin = deserializer.getLong();
        this.tout = deserializer.getLong();
    }
    /**
     * {@inheritDoc}
     *
     * @deprecated since 1.13. Use {@link #serialize(IValueSerializer)} with an array serializer instead.
     */
    @Override
    @Deprecated
    public Object[] toArray() {
        return new Object[] {
                this.getTimestamp(),
                this.getTraceId(),
                this.getOrderIndex(),
                this.getOperationSignature(),
                this.getClassSignature(),
                this.getCalleeOperationSignature(),
                this.getCalleeClassSignature(),
                this.getObjectId(),
                this.getCalleeObjectId(),
                this.getEoi(),
                this.getEss(),
                this.getTin(),
                this.getTout()
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerStrings(final IRegistry<String> stringRegistry) {	// NOPMD (generated code)
        stringRegistry.get(this.getOperationSignature());
        stringRegistry.get(this.getClassSignature());
        stringRegistry.get(this.getCalleeOperationSignature());
        stringRegistry.get(this.getCalleeClassSignature());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(final IValueSerializer serializer) throws BufferOverflowException {
        //super.serialize(serializer);
        serializer.putLong(this.getTimestamp());
        serializer.putLong(this.getTraceId());
        serializer.putInt(this.getOrderIndex());
        serializer.putString(this.getOperationSignature());
        serializer.putString(this.getClassSignature());
        serializer.putString(this.getCalleeOperationSignature());
        serializer.putString(this.getCalleeClassSignature());
        serializer.putInt(this.getObjectId());
        serializer.putInt(this.getCalleeObjectId());
        serializer.putInt(this.getEoi());
        serializer.putInt(this.getEss());
        serializer.putLong(this.getTin());
        serializer.putLong(this.getTout());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?>[] getValueTypes() {
        return TYPES; // NOPMD
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getValueNames() {
        return PROPERTY_NAMES; // NOPMD
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return SIZE;
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated This record uses the {@link kieker.common.record.IMonitoringRecord.Factory} mechanism. Hence, this method is not implemented.
     */
    @Override
    @Deprecated
    public void initFromArray(final Object[] values) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;

        final CallOperationObjectEventExtend castedRecord = (CallOperationObjectEventExtend) obj;
        if (this.getLoggingTimestamp() != castedRecord.getLoggingTimestamp()) return false;
        if (this.getTimestamp() != castedRecord.getTimestamp()) return false;
        if (this.getTraceId() != castedRecord.getTraceId()) return false;
        if (this.getOrderIndex() != castedRecord.getOrderIndex()) return false;
        if (!this.getOperationSignature().equals(castedRecord.getOperationSignature())) return false;
        if (!this.getClassSignature().equals(castedRecord.getClassSignature())) return false;
        if (!this.getCalleeOperationSignature().equals(castedRecord.getCalleeOperationSignature())) return false;
        if (!this.getCalleeClassSignature().equals(castedRecord.getCalleeClassSignature())) return false;
        if (this.getObjectId() != castedRecord.getObjectId()) return false;
        if (this.getCalleeObjectId() != castedRecord.getCalleeObjectId()) return false;
        if (this.getTout() != castedRecord.getTout()) return false;
        if (this.getTin()!= castedRecord.getTin()) return false;
        if (this.getEss() != castedRecord.getEss()) return false;
        if (this.getEoi() != castedRecord.getEoi()) return false;
        return true;
    }

    public final int getEoi(){
      return   this.eoi;
    }

    public final  int getEss(){
        return this.ess;
    }

    public final long getTin() {
        return this.tin;
    }

    public final long getTout() {
        return this.tout;
    }


}
