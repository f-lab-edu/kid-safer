package com.flab.kidsafer.mapper;

import com.flab.kidsafer.domain.Kid;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KidMapper {

    int getParentId(int id);

    Kid getOneKid(int id);

    List<Kid> getAllKid(int parentId);

    void registerKid(Kid kid);

    void updateKid(Kid kid);

    void deleteKid(int id);
}
