package com.jotte.noti.dao;

import com.jotte.noti.vo.NotiVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface INotiDAO {
    List<NotiVO> getNotiList(String userUuid);
    int insertNoti(NotiVO noti);
    int deleteNoti(String userUuid);
    int markAsRead(@Param("userUuid") String userUuid,
                   @Param("notiUuid") String notiUuid);
}
