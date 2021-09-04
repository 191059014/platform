package com.hb.platform.hbmanage.web.config.aspect;

import com.hb.platform.unic.base.model.BaseDO;
import com.hb.platform.unic.rbac.container.RbacContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 基础数据库字段处理切面
 *
 * @version v0.1, 2021/8/24 21:06, create by huangbiao.
 */
@Aspect
@Component
@Slf4j
public class BasePropertyAspect {

    /**
     * 所有插入的方法
     */
    @Pointcut("execution(public * com.hb.platform..*.*Mapper.insert*(..))")
    public void insertPointcut() {}

    /**
     * 所有更新的方法
     */
    @Pointcut("execution(public * com.hb.platform..*.*Mapper.update*(..))")
    public void updatePointcut() {}

    /**
     * 前通知
     *
     * @param joinPoint
     *            调用参数
     */
    @Before("insertPointcut()")
    private void beforeInsert(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object obj = args[0];
        if (obj instanceof BaseDO) {
            BaseDO baseDO = (BaseDO)obj;
            baseDO.setCreator(RbacContext.getCurrentUser().getUserName());
            baseDO.setUpdator(RbacContext.getCurrentUser().getUserName());
        }
    }

    /**
     * 前通知
     *
     * @param joinPoint
     *            调用参数
     */
    @Before("updatePointcut()")
    private void beforeUpdate(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object obj = args[0];
        if (obj instanceof BaseDO) {
            BaseDO baseDO = (BaseDO)obj;
            baseDO.setUpdator(RbacContext.getCurrentUser().getUserName());
        }
    }

}
