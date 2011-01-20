/*
 * Copyright (c) 2010 Mysema Ltd.
 * All rights reserved.
 *
 */
package com.mysema.query.types.query;

import com.mysema.query.QueryMetadata;
import com.mysema.query.types.SubQueryExpression;
import com.mysema.query.types.Visitor;
import com.mysema.query.types.expr.EBoolean;
import com.mysema.query.types.expr.ETime;

/**
 * Single result subquery
 *
 * @author tiwe
 *
 * @param <A>
 */
public final class TimeSubQuery<A extends Comparable<?>> extends ETime<A> implements SubQueryExpression<A>{

    private static final long serialVersionUID = -64156984110154969L;

    private final SubQueryMixin<A> subQueryMixin;

    public TimeSubQuery(Class<A> type, QueryMetadata md) {
        super(type);
        subQueryMixin = new SubQueryMixin<A>(this,md);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public boolean equals(Object o) {
       return subQueryMixin.equals(o);
    }

    @Override
    public EBoolean exists() {
        return subQueryMixin.exists();
    }

    @Override
    public QueryMetadata getMetadata() {
        return subQueryMixin.getMetadata();
    }

    @Override
    public int hashCode(){
        return subQueryMixin.hashCode();
    }

    @Override
    public EBoolean notExists() {
        return subQueryMixin.notExists();
    }

}