package com.kylin.kylinoj.service;

import com.kylin.kylinoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.kylin.kylinoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kylin.kylinoj.model.entity.User;

/**
* @author 97903
* @description 针对表【question_submit(题目提交信息表)】的数据库操作Service
* @createDate 2024-06-06 18:43:48
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return 提交记录id
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

}
