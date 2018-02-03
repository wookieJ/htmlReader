package htmlApp.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import htmlApp.htmlFile.HtmlFile;
import htmlApp.htmlFile.HtmlFiles;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainPaneController implements Initializable
{
	@FXML
	private MenuPaneController menuPaneController;

	@FXML
	private ContentPaneController contentPaneController;

	@FXML
	private ControlPaneController controlPaneController;

	@FXML
	private TreeViewPaneController treeViewPaneController;

	@FXML
	private WebPaneController webPaneController;

	@FXML
	private SplitPane splitPane;

	private HtmlFiles htmlFiles;
	public WebEngine web;
	private double memory;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		configureControls();
		configureWebView();

		htmlFiles = new HtmlFiles(); // dodaæ do konstruktora
		File fileStart = new File("sources/Wiki.txt");
		htmlFiles.getLinksFromFile(fileStart);

		if (htmlFiles.getLinks().size() > 0)
		{
			configureTreeView(fileStart.getName().substring(0, fileStart.getName().indexOf(".")));
			loadCurrentWeb();
			String file = "sources/" + htmlFiles.getLinks().get(htmlFiles.getLinkNumber()).getPath() + ".txt";
			getBackUp(file);
		}
		configureMenu();
		createNotes();
		configureContent();
	}

	private void configureMenu()
	{
		MenuItem openButton = menuPaneController.getOpenMenu();

		openButton.setOnAction(new EventHandler<ActionEvent>() // utworzenie
																// nowego roota.
																// Aktualnie
																// dodaje do
																// istniej¹cego
																// a chcemy
																// osobny
		{
			@Override
			public void handle(ActionEvent event)
			{
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new ExtensionFilter("txt", "*.txt"));

				File file = fileChooser.showOpenDialog(new Stage());
				htmlFiles.getLinksFromFile(file);

				// if(htmlFiles.getLinks().size() > 0)
				// {
				// configureTreeView(file.getName().substring(0,
				// file.getName().indexOf(".")));
				// loadCurrentWeb();
				// }
				// else
				// //wyswetlic okienko b³edu
			}
		});
	}

	private void configureWebView()
	{
		WebView br = webPaneController.getWebView();
		web = br.getEngine();
	}

	private void configureControls()
	{
		TextArea textArea = contentPaneController.getTextArea();
		Button backButton = controlPaneController.getBackButton();
		Button saveButton = controlPaneController.getSaveButton();
		Button forwardButton = controlPaneController.getForwardButton();
		ToggleButton toggleButton = controlPaneController.getEditButton();

		backButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				if (htmlFiles.getLinkNumber() > 0)
				{
					toggleButton.setSelected(false);
					textArea.setEditable(false);

					String file = "sources/" + htmlFiles.getLinks().get(htmlFiles.getLinkNumber()).getPath() + ".txt";
					doBackUp(file);
					textArea.clear();
					htmlFiles.decreaseLinkNumber();

					loadCurrentWeb();
					file = "sources/" + htmlFiles.getLinks().get(htmlFiles.getLinkNumber()).getPath() + ".txt";
					getBackUp(file);
				}
			}
		});

		forwardButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				if (htmlFiles.getLinks().size() > htmlFiles.getLinkNumber() + 1)
				{
					toggleButton.setSelected(false);
					textArea.setEditable(false);

					String file = "sources/" + htmlFiles.getLinks().get(htmlFiles.getLinkNumber()).getPath() + ".txt";
					doBackUp(file);
					textArea.clear();
					htmlFiles.increaseLinkNumber();

					loadCurrentWeb();
					file = "sources/" + htmlFiles.getLinks().get(htmlFiles.getLinkNumber()).getPath() + ".txt";
					getBackUp(file);
				}
			}
		});

		toggleButton.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent event)
			{
				if (toggleButton.isSelected())
				{
					textArea.setEditable(true);
				} else
				{
					textArea.setEditable(false);
				}
			}
		});

		saveButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				toggleButton.setSelected(false);
				textArea.setEditable(false);

				String file = "sources/" + htmlFiles.getLinks().get(htmlFiles.getLinkNumber()).getPath() + ".txt";
				doBackUp(file);
			}
		});
	}

	private void configureTreeView(String rootName)
	{
		TreeView<String> treeView = treeViewPaneController.getTreeView();
		TextArea textArea = contentPaneController.getTextArea();
		ToggleButton toggleButton = controlPaneController.getEditButton();

		TreeItem<String> rootNode = new TreeItem<String>(rootName); // Dodanie
																	// nowego
																	// roota na
																	// podstawie
																	// elementu
																	// 1
																	// (get(1);
		rootNode.setExpanded(true);

		for (HtmlFile file : htmlFiles.getLinks())
		{
			TreeItem<String> lesson = new TreeItem<String>(file.getDescription());
			boolean found = false;

			for (TreeItem<String> lessonLeaf : rootNode.getChildren())
			{
				if (lessonLeaf.getValue().contentEquals(file.getLesson()))
				{
					lessonLeaf.getChildren().add(lesson);
					found = true;
					break;
				}
			}
			if (!found)
			{
				TreeItem<String> depNode = new TreeItem<String>(file.getLesson());
				rootNode.getChildren().add(depNode);
				depNode.getChildren().add(lesson);
			}
		}

		treeView.setRoot(rootNode);

		treeView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if (event.getClickCount() == 2)
				{
					String tmp = treeView.getSelectionModel().getSelectedItem().getValue();
					int counter = 0;
					for (HtmlFile h : htmlFiles.getLinks()) // inny sposób na
															// pozyskanie numeru
															// poniewa¿ teraz
															// musza byæ
															// unikalne
					{
						if (h.getDescription().equals(tmp))
							break;
						else
							counter++;
					}

					if (counter < htmlFiles.getLinks().size())
					{
						toggleButton.setSelected(false);
						textArea.setEditable(false);

						String file = "sources/" + htmlFiles.getLinks().get(htmlFiles.getLinkNumber()).getPath()
								+ ".txt";
						doBackUp(file);
						textArea.clear();
						htmlFiles.setLinkNumber(counter);

						loadCurrentWeb();
						file = "sources/" + htmlFiles.getLinks().get(htmlFiles.getLinkNumber()).getPath() + ".txt";
						getBackUp(file);
					}
				}
			}
		});
	}

	private void configureContent()
	{
		TextArea textArea2 = contentPaneController.getTextArea();

		textArea2.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				double[] dividersPositions;

				if (event.getClickCount() == 2)
				{
					dividersPositions = splitPane.getDividerPositions();
					if (dividersPositions[1] >= 0.61)
					{
						memory = dividersPositions[1];
						splitPane.setDividerPositions(dividersPositions[0], 0.6);
					} else
						splitPane.setDividerPositions(dividersPositions[0], memory);
				}
			}
		});
	}

	public void createNotes()
	{
		File file;
		for (HtmlFile hF : htmlFiles.getLinks())
		{
			file = new File("sources/" + hF.getPath() + ".txt");
			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public void doBackUp(String file)
	{
		TextArea textArea = contentPaneController.getTextArea();
		try (FileWriter fileWriter = new FileWriter(file); BufferedWriter writer = new BufferedWriter(fileWriter);)
		{
			writer.write(textArea.getText());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void getBackUp(String file)
	{
		TextArea textArea = contentPaneController.getTextArea();
		String tmp = "";
		StringBuilder strB = new StringBuilder();
		try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader);)
		{
			while (tmp != null)
			{
				tmp = reader.readLine();
				if (tmp != null)
				{
					strB.append(tmp + "\n\r");
				}
			}
			textArea.setText(strB.toString());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void loadCurrentWeb()
	{
		 web.load(getClass().getResource(htmlFiles.getPath(htmlFiles.getLinkNumber())).toExternalForm());
	}
}