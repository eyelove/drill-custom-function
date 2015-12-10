package org.apache.drill.contrib.function;

import org.apache.drill.exec.expr.DrillSimpleFunc;
import org.apache.drill.exec.expr.annotations.FunctionTemplate;
import org.apache.drill.exec.expr.annotations.Output;
import org.apache.drill.exec.expr.annotations.Param;
import org.apache.drill.exec.expr.holders.IntHolder;
import org.apache.drill.exec.expr.holders.NullableVarCharHolder;

@FunctionTemplate(
        name = "from_oid",
        scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL
)
public class TimestampFromObjectIdFunc implements DrillSimpleFunc  {
	
	@Param 
    NullableVarCharHolder input;

	@Output
	IntHolder output;
	
	public void setup() {
		
	}
	
	public void eval() {
        String stringValue = org.apache.drill.exec.expr.fn.impl.StringFunctionHelpers.toStringFromUTF8(input.start, input.end, input.buffer);

        output.value = Integer.parseInt(stringValue.substring(0, 8), 16);
	}
	
}
