package com.mealbuddy.agent.builder;

import com.mealbuddy.agent.loader.PromptLoader;
import io.agentscope.core.ReActAgent;
import io.agentscope.core.memory.InMemoryMemory;
import io.agentscope.core.model.Model;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EvaluationJudgeAgentBuilder {
    private final Model lightModel;
    private final PromptLoader promptLoader;

    public EvaluationJudgeAgentBuilder(@Qualifier("MealbuddyLightChatModel") Model lightModel, PromptLoader promptLoader) {
        this.lightModel = lightModel;
        this.promptLoader = promptLoader;
    }

    public ReActAgent build() {
        return ReActAgent.builder()
                .name("diet_evaluation_judge_agent")
                .model(lightModel)
                .sysPrompt(promptLoader.load("mealbuddy/prompts/evaluation-judge.txt"))
                .memory(new InMemoryMemory())
                .build();
    }
}