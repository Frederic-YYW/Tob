package com.hw.tobcore.api.base;

import com.iristar.center.entity.base.BaseEntity;
import com.iristar.center.entity.base.PageVO;
import com.iristar.center.entity.vo.ResultVO;
import com.iristar.center.enums.ResultEnum;
import com.iristar.center.exception.ParamErrorException;
import com.iristar.center.service.BaseService;
import com.iristar.center.util.JsonUtils;
import com.iristar.center.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


@Slf4j
public class BaseApiV2<Entity extends BaseEntity, I extends BaseService<Entity>> {

    public String errMsg;
    public int code;
    @Autowired
    public I baseService;
    public HttpServletRequest request;
    public boolean success;


    /**
     * 参数校验
     *
     * @param result
     * @return
     */
    public boolean checkParameters(BindingResult result) {
        if (result.hasErrors()) {
            FieldError fieldError = result.getFieldError();
            errMsg = "[ " + fieldError.getField() + " ]=" + fieldError.getRejectedValue() + " ," + fieldError.getDefaultMessage();
            code = ResultEnum.PARAM_ERROR.getCode();
            log.error("[checkParameters error] errMsg={}", errMsg + result);
            return false;
        }
        return true;
    }

    /**
     * 通过id集合删除,复写实现
     *
     * @param ids
     * @return
     */
    public ResultVO deleteByIdlist(String[] ids) {
        return null;
    }

    /**
     * 根据id 删除一个
     *
     * @param id
     * @return
     */
    public ResultVO deleteById(Object id) {
        if (id == null) {
            log.error("[deleteById is error ] id={}", id);
            throw new ParamErrorException(ResultEnum.PARAM_ERROR, "删除数据的id不能为空");
        }
        success = baseService.deleteByPrimaryKey(id);
        return success ? ResultUtils.success() : ResultUtils.error("删除失败");
    }

    /**
     * 添加一个
     *
     * @param entity
     * @return
     */
    public ResultVO add(Entity entity) {
        if (entity == null) {
            log.error("[add is error ] data={}", entity);
            throw new ParamErrorException(ResultEnum.PARAM_ERROR, "添加的数据为空");
        }
        String save = baseService.save(entity);
        return StringUtils.isNotBlank(save) ? ResultUtils.success() : ResultUtils.error("添加失败");
    }

    /**
     * 批量添加,复写实现
     *
     * @param entity
     * @return
     */
    public ResultVO add(List<Entity> entity) {
        return null;
    }


    /**
     * 根据id 动态更新
     *
     * @param entity
     * @return
     */
    public ResultVO updateByPrimaryKey(Entity entity) {
        if (entity == null) {
            log.error("[updateSelectiveById is error ] data={}", JsonUtils.objectToJson(entity));
            throw new ParamErrorException(ResultEnum.PARAM_ERROR, "更新数据不能为空");
        }
        success = baseService.updateByPrimaryKey(entity);
        return success ? ResultUtils.success() : ResultUtils.error("更新失败");
    }


    /**
     * 分页列表展示
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ResultVO list(int pageNum, int pageSize) {
        Page<Entity> page = baseService.findByPage(new HashMap<>(), pageNum, pageSize);
        PageVO pageVO = new PageVO();
        pageVO.setCount(page.getTotalElements());
        pageVO.setPageSize(page.getTotalPages());
        pageVO.setRows(page.getContent());
        return ResultUtils.success(pageVO);
    }

}

