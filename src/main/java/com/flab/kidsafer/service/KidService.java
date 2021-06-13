package com.flab.kidsafer.service;

import com.flab.kidsafer.domain.Kid;
import com.flab.kidsafer.error.exception.KidBirthYearInvalidException;
import com.flab.kidsafer.error.exception.KidNotFoundException;
import com.flab.kidsafer.error.exception.KidParentNotMatchException;
import com.flab.kidsafer.mapper.KidMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KidService {

    @Autowired
    private KidMapper kidMapper;

    public Kid getOneKid(int id) {
        Kid kid = kidMapper.getOneKid(id);
        if (kid == null) {
            throw new KidNotFoundException();
        }
        return kid;
    }

    public List<Kid> getAllKid(int parentId) {
        return kidMapper.getAllKid(parentId);
    }

    public int registerKid(Kid kid) {
        kidMapper.registerKid(kid);
        return kid.getId();
    }

    public void updateKid(Kid kid, int parentId) {
        isSameParent(kid, parentId);
        kidMapper.updateKid(kid);
    }

    public void deleteKid(int id, int parentId) {
        isSameParent(getOneKid(id), parentId);
        Kid kid = getOneKid(id);
        kidMapper.deleteKid(id);
    }

    public void isSameParent(Kid kid, int parentId) {
        if (kid.getParentId() != parentId) {
            throw new KidParentNotMatchException();
        }
    }
}
