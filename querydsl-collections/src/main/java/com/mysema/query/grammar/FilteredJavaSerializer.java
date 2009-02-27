/*
 * Copyright (c) 2009 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query.grammar;

import java.util.List;

import com.mysema.query.grammar.Ops.Op;
import com.mysema.query.grammar.types.Expr;
import com.mysema.query.grammar.types.Path;

/**
 * FilteredJavaSerializer provides
 *
 * @author tiwe
 * @version $Id$
 */
public class FilteredJavaSerializer extends JavaSerializer{
    
    private boolean skipPath = false;
    
    private List<Expr<?>> exprs;
    
    private boolean inNotOperation = false;
    
    public FilteredJavaSerializer(JavaOps ops, List<Expr<?>> expressions) {
        super(ops);
        this.exprs = expressions;
    }
    
    @Override
    protected void visitOperation(Class<?> type, Op<?> operator, Expr<?>... args) {
        if (!skipPath){
            boolean skip = false;
            for (Expr<?> expr : args){
                if (expr instanceof Path){
                    if (!exprs.contains(((Path<?>)expr).getRoot())) skip = true;
                }
            }
            if (skip){
                skipPath = true;    
            }else{
                boolean old = inNotOperation;
                inNotOperation = (operator == Ops.NOT) ? !old : old;
                super.visitOperation(type, operator, args);
                inNotOperation = old;
            }                
        }
        if (skipPath){        
            if (type.equals(Boolean.class)){
                append(inNotOperation ? "false" : "true");
                skipPath = false;
            }
        }        
    }
    
}
