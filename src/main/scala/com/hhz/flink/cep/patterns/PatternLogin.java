package com.hhz.flink.cep.patterns;

import com.hhz.flink.cep.pojo.LoginEvent;
import org.apache.flink.cep.scala.pattern.Pattern;

import javax.script.Invocable;
import java.io.Serializable;

public class PatternLogin extends AbstractPattern<LoginEvent> implements Serializable {

    public PatternLogin() {
    }

    public PatternLogin(String pattern) {
        this.pattern = pattern;
    }

    private static final long serialVersionUID = -674357631108323096L;

    @Override
    public  Pattern<LoginEvent, LoginEvent> pattern() throws Exception {
        String text = generate();
        System.out.println(text);
        Invocable invocable = invocable(text);
        Pattern<LoginEvent, LoginEvent> pattern= (Pattern<LoginEvent, LoginEvent>) invocable.invokeFunction("getP");
        return pattern;
    }

    @Override
    public String begin() {
        return "import com.hhz.flink.cep.pojo.LoginEvent\n" +
                "import com.hhz.flink.cep.patterns.conditions.LogEventCondition\n" +
                "import org.apache.flink.cep.scala.pattern.Pattern\n" +
                "import org.apache.flink.streaming.api.windowing.time.Time\n" +
                "def getP(){\n" +
                "    return Pattern.<LoginEvent>";
    }

    @Override
    public String content() {
        return patternMid(this.pattern);
    }

    @Override
    public String end() {
        return "\n}";
    }

}
