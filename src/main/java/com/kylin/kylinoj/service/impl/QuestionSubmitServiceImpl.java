package com.kylin.kylinoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kylin.kylinoj.common.ErrorCode;
import com.kylin.kylinoj.exception.BusinessException;
import com.kylin.kylinoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.kylin.kylinoj.model.entity.Question;
import com.kylin.kylinoj.model.entity.QuestionSubmit;
import com.kylin.kylinoj.model.entity.User;
import com.kylin.kylinoj.model.enums.QuestionSubmitLanguageEnum;
import com.kylin.kylinoj.model.enums.QuestionSubmitStatusEnum;
import com.kylin.kylinoj.service.QuestionService;
import com.kylin.kylinoj.service.QuestionSubmitService;
import com.kylin.kylinoj.mapper.QuestionSubmitMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 97903
 * @description 针对表【question_submit(题目提交信息表)】的数据库操作Service实现
 * @createDate 2024-06-06 18:43:48
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {
    @Resource
    private QuestionService questionService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return 提交记录id
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        //todo 校验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误！");
        }
        long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmit.getCode());
        questionSubmit.setLanguage(questionSubmit.getLanguage());
        //设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败！");
        }
        return questionSubmit.getId();
//        // 锁必须要包裹住事务方法
//        QuestionSubmitService questionSubmitService = (QuestionSubmitService) AopContext.currentProxy();
//        synchronized (String.valueOf(userId).intern()) {
//            return questionSubmitService.doQuestionSubmitInner(userId, questionId);
//        }
    }

}




