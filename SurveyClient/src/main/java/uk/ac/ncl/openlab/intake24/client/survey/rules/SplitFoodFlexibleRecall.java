/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.rules;

import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.SplitFoodPrompt;

public class SplitFoodFlexibleRecall implements PromptRule<FoodEntry, FoodOperation> {

    /**
     * Experimental. Flexible recall. Compared to original ignores FLAG_FREE_ENTRY_COMPLETE.
     */

    @Override
    public Option<Prompt<FoodEntry, FoodOperation>> apply(FoodEntry data, SelectionMode selectionType, PSet<String> surveyFlags) {
        return data.accept(new FoodEntry.Visitor<Option<Prompt<FoodEntry, FoodOperation>>>() {
            @Override
            public Option<Prompt<FoodEntry, FoodOperation>> visitRaw(RawFood food) {
                if (food.applySplit())
                    return Option.<Prompt<FoodEntry, FoodOperation>>some(new SplitFoodPrompt(food));
                else
                    return Option.none();
            }

            @Override
            public Option<Prompt<FoodEntry, FoodOperation>> visitEncoded(final EncodedFood food) {
                return Option.none();
            }

            @Override
            public Option<Prompt<FoodEntry, FoodOperation>> visitTemplate(TemplateFood food) {
                return Option.none();
            }

            @Override
            public Option<Prompt<FoodEntry, FoodOperation>> visitMissing(MissingFood food) {
                return Option.none();
            }

            @Override
            public Option<Prompt<FoodEntry, FoodOperation>> visitCompound(CompoundFood food) {
                return Option.none();
            }
        });

    }

    @Override
    public String toString() {
        return "Try to split the food description";
    }

    public static WithPriority<PromptRule<FoodEntry, FoodOperation>> withPriority(int priority) {
        return new WithPriority<PromptRule<FoodEntry, FoodOperation>>(new SplitFoodFlexibleRecall(), priority);
    }
}