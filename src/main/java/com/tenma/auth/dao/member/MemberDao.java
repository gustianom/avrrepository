package com.tenma.auth.dao.member;


import com.tenma.auth.dao.Dao;
import com.tenma.auth.model.MemberModel;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PT TENMA BRIGHT SKY Generator
 * Copyright (c) 2011. PT TENMA BRIGHT SKY. ALL Right Reserved.
 * Generator Version 1.1.
 * Generated on Fri Apr 25 16:21:32 WIB 2014
 */
public class MemberDao extends Dao {
    public MemberDao(SqlSession session) {
        super(session);
    }

    public MemberModel getMember(MemberModel model) {
        return (MemberModel) session.selectOne("getMember", model);
    }

    public String getMemberName(MemberModel model) {
        return (String) session.selectOne("getMemberName", model);
    }

    public int updateJustProfile(MemberModel model) {
        return session.update("updateJustProfile", model);
    }

    public String getMemberProfile(MemberModel model) {
        return (String) session.selectOne("getMemberProfile", model);
    }

    public int insertMember(MemberModel systemProperty) {
        int result = 0;
        result = session.insert("insertMember", systemProperty);
        return result;
    }

    public int updateMember(MemberModel systemProperty) {
        int result = 0;
        result = session.update("updateMember", systemProperty);
        return result;
    }

    public int deleteMember(MemberModel systemProperty) {
        int result = 0;
        result = session.delete("deleteMember", systemProperty);
        return result;
    }


    public List listMember() {
        List list = session.selectList("listMember", null);
        return list;
    }

    public int countTotalList(HashMap parameterObject) {
        Integer count = (Integer) session.selectOne("countTotalMember", parameterObject);
        return count == null ? 0 : count.intValue();
    }

    public int getMemberSequence() {
        Integer count = (Integer) session.selectOne("memberSequence");
        return count == null ? 0 : count.intValue();
    }

    public List<MemberModel> listAvailableMemberMap(HashMap parameterObject, boolean includeNavigation) {
        if (includeNavigation)
            return session.selectList("listMemberMap", parameterObject);
        else
            return session.selectList("listAllMemberMap", parameterObject);
    }
}
