package com.qh.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qh.basic.domain.DcVisitor;
import com.qh.basic.domain.vo.DcVisitorVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 访客信息Mapper接口
 *
 * @author 汪俊杰
 * @date 2021-01-14
 */
public interface DcVisitorMapper extends BaseMapper<DcVisitor> {
    /**
     * 根据条件分页查询列表
     *
     * @param page 分页信息
     * @param map  学生信息
     * @return 学生信息集合信息
     */
    IPage<DcVisitorVo> selectListByPage(IPage<DcVisitor> page,  @Param("map") Map<String, Object> map);
}
