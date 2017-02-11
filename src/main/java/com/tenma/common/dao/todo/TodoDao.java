package com.tenma.common.dao.todo;

import com.tenma.auth.dao.Dao;
import com.tenma.model.common.TodoModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class TodoDao extends Dao {
    public TodoDao(SqlSession session) {
        super(session);
    }

    public TodoModel getTodoDetail(TodoModel model) {
        TodoModel m = null;
        m = (TodoModel) session.selectOne("getTodobject", model);
        return m;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("getTodoCount", parameterObject);
        return count == null ? 0 : count.intValue();
    }


    public List getTodoList(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("getTodoList", parameterObject);
        else
            return session.selectList("getAllTodoList", parameterObject);
    }
}

