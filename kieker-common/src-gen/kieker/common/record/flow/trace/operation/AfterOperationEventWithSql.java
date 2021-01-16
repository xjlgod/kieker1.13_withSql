/***************************************************************************
 * Copyright 2017 Kieker Project (http://kieker-monitoring.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package kieker.common.record.flow.trace.operation;

import kieker.common.record.io.IValueDeserializer;
import kieker.common.record.io.IValueSerializer;
import kieker.common.util.registry.IRegistry;

import java.nio.BufferOverflowException;


/**
 * @author Jan Waller
 * API compatibility: Kieker 1.13.0
 * 
 * @since 1.6
 */
public class AfterOperationEventWithSql extends AbstractOperationEvent  {
	private static final long serialVersionUID = 5136385647891836891L;


	public static final String TABLE_NAME = "";

	/** sqlProperty declarations. */
	private final String tableName;

	public String getTableName() {
		return tableName;
	}

	/** Descriptive definition of the serialization size of the record. */
	public static final int SIZE = TYPE_SIZE_LONG // IEventRecord.timestamp
			+ TYPE_SIZE_LONG // ITraceRecord.traceId
			+ TYPE_SIZE_INT // ITraceRecord.orderIndex
			+ TYPE_SIZE_STRING // IOperationSignature.operationSignature
			+ TYPE_SIZE_STRING // IClassSignature.classSignature
			+ TYPE_SIZE_STRING // IClassSignature.tableName
			;

	public static final Class<?>[] TYPES = {
			long.class, // IEventRecord.timestamp
			long.class, // ITraceRecord.traceId
			int.class, // ITraceRecord.orderIndex
			String.class, // IOperationSignature.operationSignature
			String.class, // IClassSignature.classSignature
			String.class, // IClassSignature.tableName
	};



	/** property name array. */
	private static final String[] PROPERTY_NAMES = {
			"timestamp",
			"traceId",
			"orderIndex",
			"operationSignature",
			"classSignature",
			"tableName",
	};


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
	 * @param tableName
	 * 	          tableName
	 */
	public AfterOperationEventWithSql(final long timestamp, final long traceId, final int orderIndex, final String operationSignature, final String classSignature, final
	String tableName) {
		super(timestamp, traceId, orderIndex, operationSignature, classSignature);
		this.tableName = tableName == null?TABLE_NAME:tableName;
	}

	/**
	 * This constructor converts the given array into a record.
	 * It is recommended to use the array which is the result of a call to {@link #toArray()}.
	 *
	 * @param values
	 *            The values for the record.
	 *
	 * @deprecated since 1.13. Use {@link #AfterOperationEventWithSql(IValueDeserializer)} instead.
	 */
	@Deprecated
	public AfterOperationEventWithSql(final Object[] values) { // NOPMD (direct store of values)
		super(values, TYPES);
		this.tableName = (String) values[5];
	}

	/**
	 * This constructor uses the given array to initialize the fields of this record.
	 *
	 * @param values
	 *            The values for the record.
	 * @param valueTypes
	 *            The types of the elements in the first array.
	 *
	 * @deprecated since 1.13. Use {@link #AfterOperationEventWithSql(IValueDeserializer)} instead.
	 */
	@Deprecated
	protected AfterOperationEventWithSql(final Object[] values, final Class<?>[] valueTypes) { // NOPMD (values stored directly)
		super(values, valueTypes);
		this.tableName = (String) values[5];
	}


	/**
	 * @param deserializer
	 *            The deserializer to use
	 */
	public AfterOperationEventWithSql(final IValueDeserializer deserializer) {

		super(deserializer);
		this.tableName = deserializer.getString();
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
				this.getTableName()
		};
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerStrings(final IRegistry<String> stringRegistry) {	// NOPMD (generated code)
		stringRegistry.get(this.getOperationSignature());
		stringRegistry.get(this.getClassSignature());
		stringRegistry.get(this.getTableName());
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
		serializer.putString(this.getTableName());
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
	 * @deprecated This record uses the {@link Factory} mechanism. Hence, this method is not implemented.
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

		final AfterOperationEventWithSql castedRecord = (AfterOperationEventWithSql) obj;
		if (this.getLoggingTimestamp() != castedRecord.getLoggingTimestamp()) return false;
		if (this.getTimestamp() != castedRecord.getTimestamp()) return false;
		if (this.getTraceId() != castedRecord.getTraceId()) return false;
		if (this.getOrderIndex() != castedRecord.getOrderIndex()) return false;
		if (!this.getOperationSignature().equals(castedRecord.getOperationSignature())) return false;
		if (!this.getClassSignature().equals(castedRecord.getClassSignature())) return false;
		if (!this.getTableName().equals(castedRecord.getTableName())) return false;
		return true;
	}

}
