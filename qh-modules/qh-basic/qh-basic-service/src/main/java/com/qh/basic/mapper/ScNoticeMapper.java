package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.ScClass;
import com.qh.basic.domain.ScNotice;
import com.qh.basic.domain.vo.NoticeViewVo;
import com.qh.basic.domain.vo.PushMoveAcc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学校公告记录Mapper接口
 *
 * @author 汪俊杰
 * @date 2020-11-25
 */
public interface ScNoticeMapper extends BaseMapper<ScNotice> {
    /**
     * 根据班级id或学生id查询
     *
     * @param classId 班级id
     * @param psId   学生家长id
     * @return
     */
    List<PushMoveAcc> findPushMoveAcc(@Param(value = "classId") String classId, @Param(value = "psId") String psId);

    /**
     * 修改
     *
     * @param notice
     */
    void modify(ScNotice notice);

    /**
     * 根据公告id删除
     *
     * @param noticeId 公告id
     */
    void deleteByNoticeId(String noticeId);

    /**
     * 分页查询已读未读人员列表
     *
     * @param page  分页
     * @param bizId 公告id
     * @param look  已读未读
     * @return
     */
    IPage<NoticeViewVo> selectViewListByPage(IPage<NoticeViewVo> page, @Param("bizId") String bizId,
                                     @Param("look") Integer look);
}
