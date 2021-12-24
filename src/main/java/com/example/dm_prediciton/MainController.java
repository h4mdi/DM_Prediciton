package com.example.dm_prediciton;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.FmtBool;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.output.prediction.AbstractOutput;
import weka.classifiers.evaluation.output.prediction.PlainText;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
public class MainController {

    String msg ="";

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("house",new House()) ;

        return "index";
    }

    @RequestMapping("/buildmodel")
    public String buildModel(House house) {

        //Try block to check for exceptions
        try {

            // Create J48 classifier by
            // creating object of J48 class
            Classifier cls = new RandomForest();

            // Dataset path
            String strain = "C:\\Users\\utilisa\\OneDrive\\Bureau\\PJ_DM\\ds\\train_data.csv.arff";


            // Creating bufferedreader to read the dataset
            BufferedReader br_train = new BufferedReader(new FileReader(strain));

            // Create dataset instances


            Instances train = new Instances(br_train);

            //close buffer
            br_train.close();

            // Set Target Class
            //Attribute att = new Attribute("price");
            train.setClassIndex(0);

            cls.buildClassifier(train);

            // Evaluating by creating object of Evaluation
            // class
            AbstractOutput output = new PlainText();
            output.setBuffer(new StringBuffer());
            output.setHeader(train);
            output.setAttributes("first");

            Evaluation evaluation = new Evaluation(train);

            evaluation.evaluateModel(cls, train, output);

            //System.out.println(evaluation.toSummaryString());



            // System.out.println(evaluation.toMatrixString("\nConfusion Matrix\n========\n"));


            // Cross Validate Model with 10 folds
            // evaluation.crossValidateModel(j48Classifier, datasetInstances, 10,new Random(1));

            Instance instance = new DenseInstance(11);
            instance.setDataset(train);
            instance.setClassMissing();
            instance.setValue(train.attribute("neighborhood_name"),house.getNg_name());
            instance.setValue(train.attribute("administritive_area"),house.getAdmin_area());
            instance.setValue(train.attribute("city"),2);
            instance.setValue(train.attribute("rooms"),house.getRooms());
            instance.setValue(train.attribute("bathrooms"),house.getBathrooms());
            instance.setValue(train.attribute("sqm"),house.getSqm());
            instance.setValue(train.attribute("elevator"),house.getElev());
            instance.setValue(train.attribute("bool"),house.getPool());
            instance.setValue(train.attribute("driver"),house.getDriver());
            instance.setValue(train.attribute("garden"),house.getDriver());

            double pred = cls.classifyInstance(instance) ;
            String prediction=train.classAttribute().value((int)pred );


           // System.out.println(prediction);
            msg=prediction;
            //System.out.println("CC"+msg);
            evaluation.toClassDetailsString() ;
        }

        // Catch block to handle the exceptions
        catch (Exception e) {

            // Print message on the console
            e.printStackTrace();
        }

        return "redirect:resultat";
    }

    @RequestMapping("/resultat")
    public String resultat(Model model) {
        model.addAttribute("m",msg) ;
        return "resultat";
    }



    @GetMapping("/users/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {

        final String[] header = new String[]{"price", "neighborhood_name", "administritive_area", "city", "rooms", "bathrooms", "sqm", "elevator", "bool", "driver", "garden"};

        // create the customer Maps (using the header elements for the column keys)
        final Map<String, Object> predList = new HashMap<String, Object>();
        predList.put(header[0], "1");
        predList.put(header[1], "Al Munsiyah");
        predList.put(header[2], "Riyadh");
        predList.put(header[3], "East Riyadh");
        predList.put(header[4], 4);
        predList.put(header[5], 5);
        predList.put(header[6], 240.0);
        predList.put(header[7], 0);
        predList.put(header[8], 0);
        predList.put(header[9], 0);
        predList.put(header[10], 0);


        ICsvMapWriter mapWriter = null;
        try {
            mapWriter = new CsvMapWriter(new FileWriter("target/partialWriteWithCsvMapWriter.csv"),
                    CsvPreference.STANDARD_PREFERENCE);

            final CellProcessor[] processors = new CellProcessor[]{ new NotNull(), new NotNull(),
                    new NotNull(),  new NotNull(),  new NotNull(), new NotNull(), new NotNull(), new NotNull(), new NotNull(), new NotNull(), new NotNull()};

            // write the header
            mapWriter.writeHeader(header);

            // write the customer Maps
            mapWriter.write(predList, header, processors);

        } finally {
            if (mapWriter != null) {
                mapWriter.close();
            }
        }
    }
}
