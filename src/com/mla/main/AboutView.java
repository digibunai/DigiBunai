/*
 * Copyright (C) 2017 Media Lab Asia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mla.main;

import com.mla.dictionary.DictionaryAction;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @Designing GUI window for about software
 * @author Amit Kumar Singh
 * 
 */
public class AboutView extends Application {
    private Configuration objConfiguration;
    DictionaryAction objDictionaryAction;
	
 /**
 * AboutView(Stage)
 * <p>
 * This constructor is used for individual call of class. 
 * 
 * @param       Stage primaryStage
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.stage.*;
 * @link        FabricView
 */
    public AboutView(final Stage primaryStage) {  }

 /**
 * AboutView(Configuration)
 * <p>
 * This class is used for prompting about software information. 
 * 
 * @param       Configuration objConfigurationCall
 * @author      Amit Kumar Singh
 * @version     %I%, %G%
 * @since       1.0
 * @see         javafx.scene.control.*;
 * @link        Configuration
 */
    public AboutView(Configuration objConfigurationCall) {  
        this.objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);
        Stage aboutStage = new Stage();
        aboutStage.initStyle(StageStyle.UTILITY);
        Group root = new Group();
        Scene scene = new Scene(root, 700, 500, Color.WHITE);
        
        TabPane tabPane = new TabPane();

        Tab aboutMediaLabAsiaTab = new Tab();
        Tab aboutSoftwareTab = new Tab();
        Tab softwareHistoryTab = new Tab();
        Tab aboutTeamTab = new Tab();
        Tab aboutMajorContributersTab = new Tab();
        Tab pilotDeploymentTab = new Tab();
        
        aboutMediaLabAsiaTab.setClosable(false);
        aboutSoftwareTab.setClosable(false);
        softwareHistoryTab.setClosable(false);
        pilotDeploymentTab.setClosable(false);
        aboutTeamTab.setClosable(false);
        aboutMajorContributersTab.setClosable(false);
        
        
        aboutMediaLabAsiaTab.setText("About Media Lab Asia");
        aboutSoftwareTab.setText("About Software");
        softwareHistoryTab.setText("Software History");
        pilotDeploymentTab.setText("Pilot Test Deployment");
        aboutTeamTab.setText("About Team");
        aboutMajorContributersTab.setText("About Major Contributers");
        
        aboutMediaLabAsiaTab.setTooltip(new Tooltip("About Media Lab Asia"));
        aboutSoftwareTab.setTooltip(new Tooltip("About Software"));
        softwareHistoryTab.setTooltip(new Tooltip("Software History"));
        pilotDeploymentTab.setTooltip(new Tooltip("Pilot Test Deployment"));
        aboutTeamTab.setTooltip(new Tooltip("About Team"));
        aboutMajorContributersTab.setTooltip(new Tooltip("About Major Contributers"));
        
        Label aboutMediaLabAsiaLbl = new Label("About Media Lab Asia\n\n Media Lab Asia (MLAsia) is a not for profit organization under the Ministry of Electronics and Information Technology (MeitY), Ministry of Communication & IT, GoI. We bring in the ICT solutions to benefit the common man in the areas of Healthcare, Education, Livelihood Enhancement and Empowerment of Differently Abled.");
        Label aboutSoftwareLbl = new Label("About Software\n\n The application software helps the designers/weavers in creating innovative designs and visualizing the designs in different color combinations. The saree simulation feature of the application provides complete Saree layout which can be shared to the customers to get the buy in. This initiative helps to fulfill the mandate of opening CFCs for helping weavers’ community.");
        Label softwareHistoryLbl = new Label("Software History\n\nIndia has a rich heritage of craft design and craft products. It spans from embroidery, zari, zardosi, metal work, woodcraft, stonework to toy design. But many of the traditional crafts are vanishing due less income, time consuming manual processes, and non-reusability of designs created on paper. To overcome the above issues, intervening with localization and easy to use ICT experiences are required in the increasing digital economy. The byproduct of doing the above is conserving and growing the rich Indian heritage. Media Lab Asia along with its partner IIT Kanpur developed an ICT based tool viz. Chic™ (CAD Tool for Craft) to help the artisans for 2D design making viz. Embroidery (Chikankari, Zari, Zardozi, etc.). Chic™ CAD has been deployed in Uttar Pradesh., Madhya Pradesh, Andhra Pradesh, Haryana and Delhi.\n" +
"\n" +
"During the deployment of Chic™ (CAD Tool for Craft) at Chanderi (M.P) Saree weaving cluster, Media Lab Asia recognized for reducing the time of pre-loom process and used a commercial weaving software for creating Saree designs/ weaving patterns, converting in to graphs and automatic jacquard card punching. The users feedback was multifold; complex to use, no localization (local designs & languages) and expensive (hence piracy being rampant). With our experience in Lallapura (Varanasi) on our embroidery design software training, we realized the need for weaving software customized to Banarasi cluster.\n" +
"\n" +
"Under the aegis of R&D in IT workgroup, Ministry of Electronics and IT, Media Lab Asia is developing an Open Source Software to aid the handloom weaving community of Banarasi Sarees. The software aid the weavers to create the artwork and translate the saree design to be loaded to the looms. The product is being developed under the able guidance and support of Department of Textile Technology of IIT Delhi, WSC – Delhi, WSC-Varanasi, IIHT-Varanasi and Amity School of Fashion Technology.\n" +
"\n" +
"The project is being monitored by the Project Review and Steering Group from 01/12/2015 onwards with the following constitution:\n" +
"\n" +
"    Prof. Kushal Sen, Department of Textile Technology, IIT Delhi- Chairman\n" +
"    Dr. S. K Srivastva, Scientist ‘G’, Ministry of Electronics and Information Technology, India\n" +
"    Prof. K K Goswami, Director, Indian Institute of Carpet Technology, Bhadohi\n" +
"    Sh. Muthusamy, Director, Weavers Service Centre (WSC), Delhi\n" +
"    Dr. Mukul K. Sinha, President, Expert Software Consultants\n" +
"    Sh. Tara Shanker, Scientist ‘F’, Ministry of Electronics and Information Technology, India - Member Convener\n" +
"\n" +
"The pilot testing of the project has been started at WSC Varanasi from November 15, 2016 onwards. The punching machine was installed at WSC Varanasi on 15th November 2016 and the training session was conducted on the application and punching machine from 16th November 2016 to 18th November 2016. During the pilot test, Deputy Director, WSC Varanasi Sh. S Bandyopadhyay helped a lot along with his team. The following team members were involved in the pilot test:\n" +
"\n" +
"    Mr. H.K Srivastava, Assistant Director (weaving)\n" +
"    Mr. A.K Verma, Assistant Director (weaving)\n" +
"    Mr. A.K Verma, Assistant Director (weaving)\n" +
"    Mr. Anshuman Gupta, Assistant Director (Processing)\n" +
"    Mr. Mohd Yasin, Technical Superintendent (weaving)\n" +
"    Mrs. Shaliha Afdi, Designer\n" +
"    Mr. Debashish Sengupta, Designer\n" +
"    Mr. J. Malakar, Junior Weaver (grade-II)\n" +
"    Other staff in the weaving section for lacing the jacquard cards and weaving the Saree sample");
        Label pilotDeploymentLbl = new Label("Pilot Deployment\n\nAs per the recommendations of the Project Review and Steering Group (PRSG) committee, of which the representatives of the O/o DC Handloom are members, this was decided that WSC and CFCs of Varanasi Weaving cluster may be used to pilot test the customized software and later deploy the same in the cluster. The Pilot test also involve imparting the training on digital literacy to the artisans to make them capable of using ICT for weaving. The application is being pilot tested at the following WSC and 4 CFCs of Varanasi weaving cluster.\n" +
"\n" +
"    Weavers Service Centre (WSC), Varanasi.\n" +
"    Common Facility Centre (CFC), Cholapur (Gadsara), Varanasi.\n" +
"    Common Facility Centre (CFC), Pindra (Nehia), Varanasi.\n" +
"    Common Facility Centre (CFC), Lohta (Harpalpur), Varanasi.\n" +
"    Common Facility Centre (CFC), Ramnagar, Varanasi.\n" +
"\n" +
"This pilot deployment fulfill the objective of CFCs to develop the innovative designs and fabric samples and conducting training programs in technological areas. These Centre also act as support Centre to help the handloom weavers of Varanasi in pre-loom operations like designing, jacquard card punching etc along with the provision of training to the weavers on Computer Aided Textile Designing Tool on latest designs and color trends.");
        Label aboutTeamLbl = new Label("About Team\n\n    Mr. Satya Vir Singh, Senior Research Scientist, MLAsia\n" +
"    Mr. Amit Kumar Singh, Senior Software Developer, MLAsia\n" +
"    Mr. Ashish Dochania, Project Coordinator, MLAsia\n" +
"    Mr. Ramyash Rajbhar, Technolojgy Coordinator (IT), MLAsia\n" +
"    Mr. Aatif Khan, Software Developer, MLAsia\n" +
"    Ms. Poonam, Prasad, Technical Trainee.");
        Label aboutMajorContributersLbl = new Label("About Major Contributers\n\n"+ "We immensely thank the contributions made by all the people during the course of creation of the application. We would like to thank the following specifically:\n" +
"Department of Textile Technology, IIT Delhi\n" +
"\n" +
"    Dr. Abijit Majumdar, Associate Professor, Department of Textile, Technology, IIT Delhi\n" +
"    Mr. Prakash Prakash Khude, PhD Research Scholar\n" +
"\n" +
"IIIT Delhi\n" +
"\n" +
"    Dr. Ojaswa Sharma, Assistant Professor, IIIT, Delhi\n" +
"    Dr. Chetan Arora, Assistant Professor, IIIT, Delhi\n" +
"\n" +
"Jaypee Institute of Information Technology, Noida\n" +
"\n" +
"    Prof. Sanjay Goel, Professor and Head (Deptt. of CSE)\n" +
"\n" +
"Amity School of Fashion Technology, Noida\n" +
"\n" +
"    Dr. Manpreet Manshahia, Assitant Professor, Amity School of Fashion Technology, Noida\n" +
"\n" +
"Indian Institute of Handloom Technology (IIHT), Varanasi\n" +
"\n" +
"    Mr. Panneer Selvam, Director, IIHT, Varanasi\n" +
"\n" +
"Indian Institute of Carpet Technology\n" +
"\n" +
"    Dr. Moumita Bera, Assistant Professor\n" +
"    Mr. C.S.Bajpai, Design Lab In-charge\n" +
"\n" +
"Weavers Service Center (WSC), Varanasi\n" +
"\n" +
"    Mohd. Yasin, Technical Superintendent (Weaving)\n" +
"    Mrs. Saleha Abdi, Designer\n" +
"    Mr. Debashish Sengupta, Designer\n" +
"    Mr. Jagannath Malakar, Junior Weaver\n" +
"    Mr. Sourav Koli, Trainee\n" +
"    Mr. Sudheer Kumar, Trainee\n" +
"    Mr. Anil Kumar, Trainee\n" +
"    Mr. Shalendra Prakash, Trainee\n" +
"\n" +
"Weavers Service Center (WSC), Delhi\n" +
"\n" +
"    Mr. Muthusamy, Director\n" +
"    Mr. Vikash Kumar, Assistant Director (designing)\n" +
"    Mr. K.N Oniyal, Assistant Director (Weaving)\n" +
"    Mr. Hirabhai Goel, Technical Superintendent (weaving)\n" +
"    Mr. Rakesh Singh, Designer\n" +
"    Mr. Prem Kumar, Junior Weaver\n" +
"\n" +
"Weavers/ Designer Community Varanasi\n" +
"\n" +
"    Mr. R.N Verma, Former Technical Superintendent (Weaving), WSC Varanasi\n" +
"    Mr. Abdul Quddus, Designer\n" +
"    Mr. Liyaqat Ali Ansari, Designer\n" +
"    Mr. Shadab Mohsin, Designer");

        aboutMediaLabAsiaLbl.setStyle("-fx-wrap-text:true;");
        aboutSoftwareLbl.setStyle("-fx-wrap-text:true;");
        softwareHistoryLbl.setStyle("-fx-wrap-text:true;");
        pilotDeploymentLbl.setStyle("-fx-wrap-text:true;");
        aboutTeamLbl.setStyle("-fx-wrap-text:true;");
        aboutMajorContributersLbl.setStyle("-fx-wrap-text:true;");
        
        aboutMediaLabAsiaTab.setContent(aboutMediaLabAsiaLbl);
        aboutSoftwareTab.setContent(aboutSoftwareLbl);
        softwareHistoryTab.setContent(softwareHistoryLbl);
        pilotDeploymentTab.setContent(pilotDeploymentLbl);
        aboutTeamTab.setContent(aboutTeamLbl);
        aboutMajorContributersTab.setContent(aboutMajorContributersLbl);

        tabPane.getTabs().add(aboutMediaLabAsiaTab);
        tabPane.getTabs().add(aboutSoftwareTab);
        tabPane.getTabs().add(softwareHistoryTab);
        tabPane.getTabs().add(pilotDeploymentTab);
        tabPane.getTabs().add(aboutTeamTab);
        tabPane.getTabs().add(aboutMajorContributersTab);
        
        BorderPane borderPane = new BorderPane();
        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        ScrollPane container = new ScrollPane();
        //container.setMaxWidth(500);
        container.setContent(tabPane);
        borderPane.setCenter(container);
        root.getChildren().add(borderPane);
        aboutStage.setScene(scene);
        //aboutStage.initOwner(WindowView.windowStage);
        aboutStage.setResizable(true);
        aboutStage.getIcons().add(new Image("/media/icon.png"));
        aboutStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWABOUTUS")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        aboutStage.showAndWait();
    }
     
    public void start(Stage stage) throws Exception {
        stage.initOwner(WindowView.windowStage);
        new AboutView(stage);        
        new Logging("WARNING",AboutView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    public static void main(String[] args) {   
      launch(args);    
    }            
}


