package com.tenma.common.bean.todo;

import com.tenma.common.bean.TenmaHelper;
import com.tenma.common.dao.todo.TodoDao;
import com.tenma.model.common.TodoModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class TodoHelper extends TenmaHelper {
    public int countTotalList(HashMap mapList) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TodoDao dao = new TodoDao(session);
            return dao.countTotalList(mapList);
        } finally {
            session.close();
        }
    }

    public List getListRenderer(HashMap mapList, boolean navigated) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TodoDao dao = new TodoDao(session);
            return dao.getTodoList(mapList, navigated);
        } finally {
            session.close();
        }
    }


    public TodoModel getTodoDetail(TodoModel model) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TodoDao dao = new TodoDao(session);
            return dao.getTodoDetail(model);
        } finally {
            session.close();
        }
    }

}
