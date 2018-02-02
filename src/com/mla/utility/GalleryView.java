/*
 * Copyright (C) 2017 HP
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

package com.mla.utility;

import com.mla.dictionary.DictionaryAction;
import com.mla.main.AboutView;
import com.mla.main.Configuration;
import com.mla.main.ContactView;
import com.mla.main.HelpView;
import com.mla.main.Logging;
import com.mla.main.TechnicalView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javax.imageio.ImageIO;
/**
 *
 * @author Amit Kumar Singh
 */
public class GalleryView extends Application {
    public static Stage galleryStage;
    BorderPane root;
    private Label lblStatus;
    ProgressBar progressB;
    ProgressIndicator progressI;
    
    Configuration objConfiguration = null;
    DictionaryAction objDictionaryAction = null;
    
    private Menu homeMenu;
    private Menu helpMenu;

    GridPane GP_container;
    
    public GalleryView(final Stage primaryStage) {}
    
    public GalleryView(Configuration objConfigurationCall) {
        objConfiguration = objConfigurationCall;
        objDictionaryAction = new DictionaryAction(objConfiguration);

        galleryStage = new Stage(); 
        root = new BorderPane();
        Scene scene = new Scene(root, objConfiguration.WIDTH, objConfiguration.HEIGHT, Color.WHITE);
        scene.getStylesheets().add(GalleryView.class.getResource(objConfiguration.getStrTemplate()+"/setting.css").toExternalForm());
        
        HBox footContainer = new HBox();
        progressB = new ProgressBar(0);
        progressB.setVisible(false);
        progressI = new ProgressIndicator(0);
        progressI.setVisible(false);
        lblStatus = new Label(objDictionaryAction.getWord("WELCOMETOCADTOOL"));
        lblStatus.setId("message");
        footContainer.getChildren().addAll(lblStatus,progressB,progressI);
        footContainer.setId("footContainer");
        root.setBottom(footContainer);
        
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(galleryStage.widthProperty());
        
        homeMenu  = new Menu();
        final HBox homeMenuHB = new HBox();
        homeMenuHB.getChildren().addAll(new ImageView(objConfiguration.getStrColour()+"/home.png"),new Label(objDictionaryAction.getWord("HOME")));
        homeMenu.setGraphic(homeMenuHB);
        homeMenuHB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                final Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UTILITY);
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.setResizable(false);
                dialogStage.setIconified(false);
                dialogStage.setFullScreen(false);
                dialogStage.setTitle(objDictionaryAction.getWord("ALERT"));
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, 300, 100, Color.WHITE);
                scene.getStylesheets().add(GalleryView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                final GridPane popup=new GridPane();
                popup.setId("popup");
                popup.setHgap(5);
                popup.setVgap(5);
                popup.setPadding(new Insets(25, 25, 25, 25));
                popup.add(new ImageView(objConfiguration.getStrColour()+"/stop.png"), 0, 0);
                Label lblAlert = new Label(objDictionaryAction.getWord("ALERTCLOSE"));
                lblAlert.setStyle("-fx-wrap-text:true;");
                lblAlert.setPrefWidth(250);
                popup.add(lblAlert, 1, 0);
                Button btnYes = new Button(objDictionaryAction.getWord("YES"));
                btnYes.setPrefWidth(50);
                btnYes.setId("btnYes");
                btnYes.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        dialogStage.close();
                        galleryStage.close();
                        System.gc();
                        UtilityView objUtilityView = new UtilityView(objConfiguration);
                    }
                });
                popup.add(btnYes, 0, 1);
                Button btnNo = new Button(objDictionaryAction.getWord("NO"));
                btnNo.setPrefWidth(50);
                btnNo.setId("btnNo");
                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        dialogStage.close();
                        System.gc();
                    }
                });
                popup.add(btnNo, 1, 1);
                root.setCenter(popup);
                dialogStage.setScene(scene);
                dialogStage.showAndWait();  
                me.consume();
            }
        });        
        Label helpMenuLabel = new Label(objDictionaryAction.getWord("SUPPORT"));
        helpMenuLabel.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPSUPPORT")));
        Menu helpMenu = new Menu();
        helpMenu.setGraphic(helpMenuLabel);
        MenuItem helpMenuItem = new MenuItem(objDictionaryAction.getWord("HELP"));
        helpMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/help.png"));
        helpMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {       
                HelpView objHelpView = new HelpView(objConfiguration);
            }
        });        
        MenuItem technicalMenuItem = new MenuItem(objDictionaryAction.getWord("TECHNICAL"));
        technicalMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/technical_info.png"));
        technicalMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {       
                TechnicalView objTechnicalView = new TechnicalView(objConfiguration);
            }
        });
        MenuItem aboutMenuItem = new MenuItem(objDictionaryAction.getWord("ABOUTUS"));
        aboutMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/about_software.png"));
        aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                AboutView objAboutView = new AboutView(objConfiguration);
            }
        });
        MenuItem contactMenuItem = new MenuItem(objDictionaryAction.getWord("CONTACTUS"));
        contactMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/contact_us.png"));
        contactMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ContactView objContactView = new ContactView(objConfiguration);
            }
        });
        MenuItem exitMenuItem = new MenuItem(objDictionaryAction.getWord("EXIT"));
        exitMenuItem.setGraphic(new ImageView(objConfiguration.getStrColour()+"/quit.png"));
        exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.gc();
                galleryStage.close();    
            }
        });    
        helpMenu.getItems().addAll(helpMenuItem, technicalMenuItem, aboutMenuItem, contactMenuItem, new SeparatorMenuItem(), exitMenuItem);        
        menuBar.getMenus().addAll(homeMenu, helpMenu);
        root.setTop(menuBar);
        
        TabPane tabPane = new TabPane();

        final Tab documentTab = new Tab();
        final Tab videoTab = new Tab();
        final Tab imageTab = new Tab();

        documentTab.setClosable(false);
        videoTab.setClosable(false);
        imageTab.setClosable(false);
        
        documentTab.setText(objDictionaryAction.getWord("HELPGUIDE"));
        videoTab.setText(objDictionaryAction.getWord("VIDEOGUIDE"));
        imageTab.setText(objDictionaryAction.getWord("GALLERY"));
        
        documentTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPHELPGUIDE")));
        videoTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPVIDEOGUIDE")));
        imageTab.setTooltip(new Tooltip(objDictionaryAction.getWord("TOOLTIPGALLERY")));
        
        //documentTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/fabric_library.png"));
        //videoTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/cloth_library.png"));
        //imageTab.setGraphic(new ImageView(objConfiguration.getStrColour()+"/artwork_library.png"));
        
        //documentTab.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //videoTab.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        //imageTab.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        
        //documentTab.setAlignment(Pos.TOP_CENTER);
        //videoTab.setAlignment(Pos.TOP_CENTER);
        //imageTab.setAlignment(Pos.TOP_CENTER);
        
        //documentTab.setId("container");
        //videoTab.setId("container");
        //imageTab.setId("container");
        
        //--------------------------
        ScrollPane container = new ScrollPane();
        container.setPrefSize(objConfiguration.WIDTH/1.1,objConfiguration.HEIGHT/1.5);
        
        GP_container = new GridPane();
        GP_container.setAlignment(Pos.TOP_CENTER);
        GP_container.setHgap(20);
        GP_container.setVgap(20);
        GP_container.setPadding(new Insets(1, 1, 1, 1));
        container.setContent(GP_container);
        
        //documentTab.setContent(container);
        //videoTab.setContent(container);
        //imageTab.setContent(container);
        
        tabPane.getTabs().add(documentTab);
        tabPane.getTabs().add(videoTab);
        tabPane.getTabs().add(imageTab);
        
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
                if(newTab == videoTab) {
                    populateVideo();
                }else if(newTab == documentTab) {
                    populateDocument();
                }else if(newTab == imageTab) {
                    populateImage();
                }
            }
        });
        
        populateDocument();
        
        GridPane bodyContainer = new GridPane();
        bodyContainer.setPrefSize(objConfiguration.WIDTH, objConfiguration.HEIGHT);
        bodyContainer.setId("container");
        
        Label caption = new Label(objDictionaryAction.getWord("HELP")+" "+objDictionaryAction.getWord("UTILITY"));
        caption.setId("caption");
        bodyContainer.add(caption, 0, 0, 1, 1);
        bodyContainer.add(tabPane, 0, 1, 1, 1);
        bodyContainer.add(container, 0, 2, 1, 1);
        /*
        final Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 2);
        GridPane.setColumnSpan(sepHor, 1);
        bodyContainer.getChildren().add(sepHor);
        */
        root.setCenter(bodyContainer);
        
        galleryStage.getIcons().add(new Image("/media/icon.png"));
        galleryStage.setTitle(objDictionaryAction.getWord("PROJECT")+" : "+objDictionaryAction.getWord("WINDOWFABRICSETTINGS")+" \u00A9 "+objDictionaryAction.getWord("TITLE"));
        //galleryStage.setIconified(true);
        galleryStage.setResizable(false);
        galleryStage.setScene(scene);
        galleryStage.setX(0);
        galleryStage.setY(0);
        galleryStage.show();
        galleryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                final Stage dialogStage = new Stage();
                dialogStage.initStyle(StageStyle.UTILITY);
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.setResizable(false);
                dialogStage.setIconified(false);
                dialogStage.setFullScreen(false);
                dialogStage.setTitle(objDictionaryAction.getWord("ALERT"));
                BorderPane root = new BorderPane();
                Scene scene = new Scene(root, 300, 100, Color.WHITE);
                scene.getStylesheets().add(GalleryView.class.getResource(objConfiguration.getStrTemplate()+"/style.css").toExternalForm());
                final GridPane popup=new GridPane();
                popup.setId("popup");
                popup.setHgap(5);
                popup.setVgap(5);
                popup.setPadding(new Insets(25, 25, 25, 25));
                popup.add(new ImageView(objConfiguration.getStrColour()+"/stop.png"), 0, 0);
                Label lblAlert = new Label(objDictionaryAction.getWord("ALERTCLOSE"));
                lblAlert.setStyle("-fx-wrap-text:true;");
                lblAlert.setPrefWidth(250);
                popup.add(lblAlert, 1, 0);
                Button btnYes = new Button(objDictionaryAction.getWord("YES"));
                btnYes.setPrefWidth(50);
                btnYes.setId("btnYes");
                btnYes.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        dialogStage.close();
                        galleryStage.close();
                        System.gc();
                        UtilityView objUtilityView = new UtilityView(objConfiguration);
                    }
                });
                popup.add(btnYes, 0, 1);
                Button btnNo = new Button(objDictionaryAction.getWord("NO"));
                btnNo.setPrefWidth(50);
                btnNo.setId("btnNo");
                btnNo.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        dialogStage.close();
                        System.gc();
                    }
                });
                popup.add(btnNo, 1, 1);
                root.setCenter(popup);
                dialogStage.setScene(scene);
                dialogStage.showAndWait();  
                we.consume();
            }
        });
    }
 
    /*Document Library*/
    private void populateDocument(){
        WebView contentWV = new WebView();
        contentWV.setPrefSize(888, 666);
        contentWV.setCursor(Cursor.CLOSED_HAND);
        contentWV.getEngine().load("file:///" + objConfiguration.strRoot+"\\help\\index.html"); 
        GP_container.getChildren().clear();
        GP_container.getChildren().add(contentWV);
    }

    /*Video Gallery*/
    private TreeItem<File> createNode(final File f) {
    return new TreeItem<File>(f) {
      private boolean isLeaf;
      private boolean isFirstTimeChildren = true;
      private boolean isFirstTimeLeaf = true;
      @Override
      public ObservableList<TreeItem<File>> getChildren() {
        if (isFirstTimeChildren) {
          isFirstTimeChildren = false;
          super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
      }
      @Override
      public boolean isLeaf() {
        if (isFirstTimeLeaf) {
          isFirstTimeLeaf = false;
          File f = (File) getValue();
          isLeaf = f.isFile();
        }
        return isLeaf;
      }
      private ObservableList<TreeItem<File>> buildChildren(
          TreeItem<File> TreeItem) {
        File f = TreeItem.getValue();
        if (f == null) {
          return FXCollections.emptyObservableList();
        }
        if (f.isFile()) {
          return FXCollections.emptyObservableList();
        }
        File[] files = f.listFiles();
        if (files != null) {
          ObservableList<TreeItem<File>> children = FXCollections
              .observableArrayList();
          for (File childFile : files) {
            children.add(createNode(childFile));
          }
          return children;
        }
        return FXCollections.emptyObservableList();
      }
    };
  }

    File videoF = null;
    Media objMedia = null;
    MediaPlayer objMediaPlayer = null;
    MediaView objMediaView = null;

    private void populateVideo(){
        try{
            GP_container.getChildren().clear();
            
            videoF = new File(objConfiguration.strRoot+"\\help\\media", "\\Backside view.mp4");
            objMedia = new Media(videoF.toURI().toString());
            objMediaPlayer = new MediaPlayer(objMedia);
            objMediaView = new MediaView(objMediaPlayer);
            final DoubleProperty width = objMediaView.fitWidthProperty();
            final DoubleProperty height = objMediaView.fitHeightProperty();
            width.bind(Bindings.selectDouble(objMediaView.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(objMediaView.sceneProperty(), "height"));
            objMediaView.setPreserveRatio(true);
            GP_container.add(objMediaView,1,1,2,1);
            
            Button btnPlay = new Button("Play");
            Button btnPause = new Button("Pause");
            btnPlay.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {  
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
                    objMediaPlayer.play();
                }
            });
            btnPause.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {  
                    lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
                    objMediaPlayer.pause();
                }
            });
            GP_container.add(btnPlay,1, 0, 1, 1);
            GP_container.add(btnPause,2, 0, 1, 1);
            
            TreeItem<File> root = createNode(new File(objConfiguration.strRoot+"\\help\\media"));
            TreeView treeView = new TreeView<File>(root);
            treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldVal, Object newVal) {
                    System.out.println(oldVal + " :neuer: " + newVal);
                    System.out.println(" name: " + ((TreeItem)newVal).getValue());
                    videoF = new File(((TreeItem)newVal).getValue().toString());
                    objMedia = new Media(videoF.toURI().toString());
                    objMediaPlayer = new MediaPlayer(objMedia);
                    objMediaView.setMediaPlayer(objMediaPlayer);
                }
            });
            GP_container.add(treeView,0, 0, 1, 2);
        } catch (Exception ex) {
            new Logging("SEVERE",GalleryView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    /*Image Gallery*/
    // array of supported extensions (use a List if you prefer)
    static final String[] EXTENSIONS = new String[]{
        "gif", "png", "jpg", "jpeg", "bmp" // and other formats you need
    };
    // filter to identify images based on their extensions
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    int position = 0;
    ImageView objImageView = null;
    private void populateImage(){
        try{
            GP_container.getChildren().clear();
            // File representing the folder that you select using a FileChooser
            File imageF = new File(objConfiguration.strRoot+"\\help");
            
            Button lbutton = new Button("Next >");
            Button rButton = new Button("< Previous");
            objImageView = new ImageView();

            GP_container.add(rButton,0, 0, 1, 1);
            GP_container.add(lbutton,1, 0, 1, 1);
            GP_container.add(objImageView,0, 1, 2, 1);

            if (imageF.isDirectory()) { // make sure it's a directory
                final File files[] = imageF.listFiles(IMAGE_FILTER);
                
                lbutton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {  
                        lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
                        if((position+1)<files.length){
                            BufferedImage bufferedImage = null;
                            try {
                                FadeTransition ft = new FadeTransition();
                                bufferedImage = ImageIO.read(files[position++]);
                                objImageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                                ft.setNode(objImageView);
                                ft.setDuration(new Duration(6000));
                                ft.setFromValue(1.0);
                                ft.setToValue(0.0);
                                ft.setCycleCount(0);
                                ft.setAutoReverse(true);
                                ft.play();
                                //GP_container.getChildren().remove(0,1);
                                //GP_container.add(imageView, 0, 1, 2, 1);
                                //Thread.sleep(6000);
                            } catch (final IOException ex) {
                                // handle errors here
                            }
                        }
                    }
                });
                rButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {  
                        lblStatus.setText(objDictionaryAction.getWord("ACTIONCANCEL"));
                        if((position-1)>=0){
                            BufferedImage bufferedImage = null;
                            try {
                                FadeTransition ft = new FadeTransition();
                                bufferedImage = ImageIO.read(files[position--]);
                                objImageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                                ft.setNode(objImageView);
                                ft.setDuration(new Duration(6000));
                                ft.setFromValue(1.0);
                                ft.setToValue(0.0);
                                ft.setCycleCount(0);
                                ft.setAutoReverse(true);
                                ft.play();
                                //GP_container.getChildren().remove(0,1);
                                //GP_container.add(imageView, 0, 1, 2, 1);
                                //Thread.sleep(6000);
                            } catch (final IOException ex) {
                                // handle errors here
                            }
                        }
                    }
                });
            }            
            //GP_container.getChildren().add(imageView);
        } catch (Exception ex) {
            new Logging("SEVERE",GalleryView.class.getName(),ex.toString(),ex);
            lblStatus.setText(objDictionaryAction.getWord("ERROR"));
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        new GalleryView(stage);
        new Logging("WARNING",GalleryView.class.getName(),"UnsupportedOperationException",new UnsupportedOperationException("Not supported yet."));
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {   
        launch(args);    
    }
}