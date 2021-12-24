package com.example.dm_prediciton.GradeP;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

public class RandomForest_Grade {
    public static void main(String[] args) throws Exception {
        //Try block to check for exceptions
        try {

            // Create J48 classifier by
            // creating object of J48 class
            Classifier cls = new RandomForest();
            //Classifier cls = new RandomForest();

            // Dataset path
            String strain = "C:\\Users\\utilisa\\OneDrive\\Bureau\\PJ_DM\\kc_Sr_main.arff";


            // Creating bufferedreader to read the dataset
            BufferedReader br_train = new BufferedReader(new FileReader(strain));

            // Create dataset instances


            Instances train = new Instances(br_train);

            //close buffer
            br_train.close();

            // Set Target Class
            //Attribute att = new Attribute("price");
            train.setClassIndex(11);

            cls.buildClassifier(train);

            Instances test = train;
            Evaluation eval = new Evaluation(test);
           // eval.evaluateModel(cls, test);
            eval.evaluateModel(cls, test);
            System.out.println(eval.toSummaryString(" \n ******* Algorithme J48 ******* \n",true));



            int k = 0;

            for (Instance instance : test) {
                double actual = instance.classValue();
                double prediction = eval.evaluateModelOnce(cls, instance);
                System.out.printf("%2d.%4.0f%4.0f", ++k, actual, prediction);
                System.out.println(prediction != actual ? " *" : "");
            }

            // Cross Validate Model with 10 folds
             eval.crossValidateModel(cls, test, 10,new Random(1));

        }

        // Catch block to handle the exceptions
        catch (Exception e) {

            // Print message on the console
            e.printStackTrace();
        }

    }
}
