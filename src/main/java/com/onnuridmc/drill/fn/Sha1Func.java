package com.onnuridmc.drill.fn;

import javax.inject.Inject;

import org.apache.drill.exec.expr.DrillSimpleFunc;
import org.apache.drill.exec.expr.annotations.FunctionTemplate;
import org.apache.drill.exec.expr.annotations.Output;
import org.apache.drill.exec.expr.annotations.Param;
import org.apache.drill.exec.expr.holders.NullableVarCharHolder;
import org.apache.drill.exec.expr.holders.VarCharHolder;

import io.netty.buffer.DrillBuf;

@FunctionTemplate(
        name = "sha1",
        scope = FunctionTemplate.FunctionScope.SIMPLE,
        nulls = FunctionTemplate.NullHandling.NULL_IF_NULL
)
public class Sha1Func implements DrillSimpleFunc  {

	@Param
    NullableVarCharHolder input;

    @Output
    VarCharHolder out;

    @Inject
    DrillBuf buffer;
    
	public void setup() {
		
	}

	public void eval() {
        String stringValue = org.apache.drill.exec.expr.fn.impl.StringFunctionHelpers.toStringFromUTF8(input.start, input.end, input.buffer);
		
        byte[] outputValue = null;
        
        try {
        	outputValue = encrypt(stringValue);
        } catch (Exception e) {
        	e.printStackTrace();
        }

        // put the output value in the out buffer
        out.buffer = buffer;
        out.start = 0;
        out.end = outputValue.length;
        buffer.setBytes(0, outputValue);
	}

	public static byte[] encrypt(String x) throws Exception {
		java.security.MessageDigest digest = null;
		digest = java.security.MessageDigest.getInstance("SHA-1");
		digest.reset();
		digest.update(x.getBytes("UTF-8"));
		return digest.digest();
	}
}
