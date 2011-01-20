/*
 * Copyright (c) 2010 Mysema Ltd.
 * All rights reserved.
 *
 */
package com.mysema.query.sql;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;

import org.easymock.EasyMock;
import org.junit.Test;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.sql.domain.QSurvey;

public class SQLSerializerTest {

    @Test
    public void testBoolean(){
        QSurvey s = new QSurvey("s");
        BooleanBuilder bb1 = new BooleanBuilder();
        bb1.and(s.name.eq(s.name));

        BooleanBuilder bb2 = new BooleanBuilder();
        bb2.or(s.name.eq(s.name));
        bb2.or(s.name.eq(s.name));

        String str = new SQLSerializer(SQLTemplates.DEFAULT).handle(bb1.and(bb2)).toString();
        assertEquals("s.NAME = s.NAME and (s.NAME = s.NAME or s.NAME = s.NAME)", str);
    }

    @Test
    public void testUpdate(){
        Connection connection = EasyMock.createMock(Connection.class);
        QSurvey survey = new QSurvey("survey");
        SQLUpdateClause updateClause = new SQLUpdateClause(connection,SQLTemplates.DEFAULT,survey);
        updateClause.set(survey.id, 1);
        updateClause.set(survey.name, null);
        assertEquals("update SURVEY\nset ID = ?, NAME = null", updateClause.toString());
    }

    @Test
    public void testInsert(){
        Connection connection = EasyMock.createMock(Connection.class);
        QSurvey survey = new QSurvey("survey");
        SQLInsertClause insertClause = new SQLInsertClause(connection,SQLTemplates.DEFAULT,survey);
        insertClause.set(survey.id, 1);
        insertClause.set(survey.name, null);
        assertEquals("insert into SURVEY(ID, NAME)\nvalues (?, null)", insertClause.toString());
    }
}