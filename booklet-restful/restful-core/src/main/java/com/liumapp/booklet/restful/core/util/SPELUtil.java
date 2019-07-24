package com.liumapp.booklet.restful.core.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * spring 表达式 AOP 处理工具类
 *
 * file SPELUtil.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/7/24
 */
public class SPELUtil {



    private final SpelExpressionParser parser;
    private final StandardEvaluationContext context;

    public SPELUtil(ProceedingJoinPoint pjp) {
        this.parser = new SpelExpressionParser();
        this.context = new StandardEvaluationContext();

        extractArgments(pjp);
    }

    /**
     *  得到参数名称和值 放到 spel 上下文
     * @param pjp
     */
    private void extractArgments(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();

        String[] names   = methodSignature.getParameterNames();
        Object[] args = pjp.getArgs();

        for (int i = 0; i < names.length; i++) {
            this.context.setVariable(names[i], args[i]);
        }
    }

    /**
     * 计算表达式
     *
     * @param expr
     * @return
     */
    public Object cacl(String expr) {
        if (expr == null || expr.trim().isEmpty() ) {
            return null;
        }

        // Expression expression = this.parser.parseExpression(greetingExp,
        //        new TemplateParserContext());
        // System.out.println(expression.getValue(context, String.class));

        return this.parser.parseRaw(expr).getValue(this.context);
    }
}
