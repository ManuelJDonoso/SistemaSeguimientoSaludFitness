/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package es.manueldonoso.sistemaseguimientosaludfitness.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import es.manueldonoso.sistemaseguimientosaludfitness.models.Usuario;
import es.manueldonoso.sistemaseguimientosaludfitness.util.UtilHelper;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.FileChooser;

import javafx.util.StringConverter;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.UsuarioDAO;
import es.manueldonoso.sistemaseguimientosaludfitness.models.dao.UsuarioDAOImpl;
import es.manueldonoso.sistemaseguimientosaludfitness.util.DatabaseHelper;
import java.awt.Dimension;
import javafx.embed.swing.SwingFXUtils;

import javafx.scene.control.ButtonType;

import javafx.embed.swing.SwingNode;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;

/**
 * FXML Controller class
 *
 * @author donpe
 */
public class AltaClientesController implements Initializable {

    @FXML
    private ToggleGroup sexo;
    @FXML
    private Spinner<Integer> sp_Altura;
    @FXML
    private Spinner<Double> sp_Peso;
    @FXML
    private JFXTextField tf_nombre;
    @FXML
    private JFXTextField tfPrimerApellido;
    @FXML
    private JFXTextField tfSegundoApellido;
    @FXML
    private JFXTextField tfDNI;
    @FXML
    private JFXTextField tfIMC;
    @FXML
    private JFXTextField tfDireccion;
    @FXML
    private JFXTextField tfPoblacion;
    @FXML
    private JFXTextField tfCP;
    @FXML
    private JFXTextField tfTel;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private ImageView ivFoto;
    @FXML
    private JFXButton btnCamara;
    @FXML
    private JFXButton btnSubirFoto;
    @FXML
    private JFXTextArea TAAnotaciones;
    @FXML
    private Label lb_DNIError;
    @FXML
    private JFXButton btnResert;
    @FXML
    private JFXTextField tfMetabolismo;
    @FXML
    private JFXTextField tfGrasaCorporal;
    @FXML
    private JFXTextField tfProteina;
    @FXML
    private JFXTextField tfGrasaVisceral;
    @FXML
    private JFXTextField tfPesoIdeal;
    @FXML
    private JFXRadioButton rbtn_hombre;
    @FXML
    private JFXRadioButton rbtn_mujer;
    @FXML
    private DatePicker dpfechaNac;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 250, 180, 1);

        // Personalizar el formato mostrado (ej. "180 cm") sin afectar el valor real
        valueFactory.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return value + " cm";
            }

            @Override
            public Integer fromString(String text) {
                // Eliminar "cm" si el usuario escribe manualmente
                try {
                    return Integer.parseInt(text.replace("cm", "").trim());
                } catch (NumberFormatException e) {
                    return 180; // Valor por defecto si hay error
                }
            }
        });

        sp_Altura.setValueFactory(valueFactory);

        SpinnerValueFactory.DoubleSpinnerValueFactory pesoFactory
                = new SpinnerValueFactory.DoubleSpinnerValueFactory(10.00, 300.00, 80.00, 0.01);

        pesoFactory.setConverter(new StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                return String.format("%.2f kg", value);
            }

            @Override
            public Double fromString(String text) {
                try {
                    return Double.parseDouble(text.replace("kg", "").trim());
                } catch (NumberFormatException e) {
                    return sp_Peso.getValue(); // ← mantener el valor actual si hay error
                }
            }
        });

        sp_Peso.setValueFactory(pesoFactory);
        sp_Peso.setEditable(true); // ← asegúrate de que sea editable
        sp_Peso.getEditor().setText(pesoFactory.getConverter().toString(sp_Peso.getValue()));

        // Listener para cambios en el peso
        sp_Peso.valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarIMC();
        });

// Listener para cambios en la altura
        sp_Altura.valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarIMC();
        });

        actualizarIMC();
    }

    @FXML
    private void oitextChanged_DNI(InputMethodEvent event) {
    }

    @FXML
    private void OAbtnGuardar(ActionEvent event) {
        Usuario user = new Usuario();

        user.setFechaNacimiento(dpfechaNac.getValue());
        user.setNombre(tf_nombre.getText().trim());
        user.setApellido1(tfPrimerApellido.getText().trim());
        user.setApellido2(tfSegundoApellido.getText().trim());
        user.setDni(tfDNI.getText().trim());
        String sex = (rbtn_hombre.isSelected()) ? "hombre" : "mujer";
        user.setSexo(sex);
        user.setAltura((sp_Altura.getValue() + "").trim());
        user.setPeso((sp_Peso.getValue() + "").trim());
        user.setImc(tfIMC.getText().trim());
        user.setDireccion(tfDireccion.getText().trim());
        user.setPoblacion(tfPoblacion.getText());
        user.setCp(tfCP.getText());
        user.setTelefono(tfTel.getText().trim());
        user.setGrasac(tfGrasaCorporal.getText().trim());
        user.setProteina(tfProteina.getText().trim());
        user.setMetabolismoV(tfMetabolismo.getText().trim());
        user.setGrasaV(tfGrasaVisceral.getText());
        user.setPesoIdeal(tfPesoIdeal.getText());
        user.setAnotaciones(TAAnotaciones.getText());
        user.setFechaAlta(LocalDate.now());

        // Guardar la imagen si hay una seleccionada
        if (ivFoto.getImage() != null && ivFoto.getProperties().containsKey("imageFile")) {
            File imageFile = (File) ivFoto.getProperties().get("imageFile");
            String dni = tfDNI.getText().trim();

            if (!dni.isEmpty()) {
                try {
                    // Crear directorio si no existe
                    Path userImageDir = Paths.get("data", "images", dni);
                    Files.createDirectories(userImageDir);

                    // Generar nombre del archivo con DNI y fecha actual
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                    String formattedDateTime = now.format(formatter);

                    // Nombre del archivo: DNI_FECHA.extensión
                    String fileName = String.format("%s_%s%s",
                            dni,
                            formattedDateTime,
                            getFileExtension(imageFile.getName()));

                    // Copiar la imagen al directorio destino
                    Path destination = userImageDir.resolve(fileName);
                    Files.copy(imageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

                    // Guardar la ruta en el usuario
                    user.setDirFoto(destination.toString());

                    // Mostrar mensaje de éxito
                    mostrarAlerta("Éxito", "Imagen guardada correctamente como: " + fileName, Alert.AlertType.INFORMATION);

                } catch (IOException e) {
                    e.printStackTrace();
                    mostrarAlerta("Error al guardar la imagen", "No se pudo guardar la imagen del usuario.");
                }
            } else {
                mostrarAlerta("DNI requerido", "Debe ingresar un DNI para guardar la imagen.");
            }
        }

      
        UsuarioDAO dao = new UsuarioDAOImpl(DatabaseHelper.conectarddbb());
        
        dao.insertar(user);
    }

    @FXML
    private void OAbtnCamara(ActionEvent event) {

        // Verificar si hay cámaras disponibles
    if (Webcam.getWebcams().isEmpty()) {
        mostrarAlerta("Cámara no disponible", "No se detectó ninguna cámara conectada.");
        return;
    }

    // Configurar la cámara con una resolución adecuada
    Webcam webcam = Webcam.getDefault();
    webcam.setViewSize(WebcamResolution.VGA.getSize());

    // Crear panel de Swing para el preview con tamaño fijo
    WebcamPanel panel = new WebcamPanel(webcam);
    panel.setFPSDisplayed(false);
    panel.setDisplayDebugInfo(false);
    panel.setImageSizeDisplayed(false);
    panel.setMirrored(true);
    panel.setPreferredSize(new Dimension(640, 480));

    // Crear diálogo de JavaFX
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Tomar Foto");
    dialog.setHeaderText("Posiciónese para la foto");

    // Crear botones personalizados
    ButtonType tomarFotoButtonType = new ButtonType("Tomar Foto", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(tomarFotoButtonType, ButtonType.CANCEL);

    // Convertir el panel de Swing a JavaFX
    SwingNode swingNode = new SwingNode();
    swingNode.setContent(panel);

    // Diseño del diálogo con tamaño fijo
    VBox vbox = new VBox(swingNode);
    vbox.setPadding(new Insets(10));
    vbox.setStyle("-fx-background-color: white;");
    vbox.setMinSize(660, 550); // Tamaño suficiente para el preview
    vbox.setPrefSize(660, 550);
    
    dialog.getDialogPane().setContent(vbox);
    dialog.getDialogPane().setMinSize(680, 580);
    dialog.getDialogPane().setPrefSize(680, 580);

    // Configurar el tamaño de la ventana del diálogo
    dialog.setResizable(true);

    // Mostrar diálogo y esperar respuesta
    Optional<ButtonType> result = dialog.showAndWait();

    if (result.isPresent() && result.get() == tomarFotoButtonType) {
        try {
            // Capturar imagen
            BufferedImage bufferedImage = webcam.getImage();
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);

            // Mostrar en el ImageView
            ivFoto.setImage(image);

            // Guardar la imagen temporalmente
            File tempFile = File.createTempFile("webcam_", ".jpg");
            ImageIO.write(bufferedImage, "jpg", tempFile);
            ivFoto.getProperties().put("imageFile", tempFile);

            mostrarAlerta("Foto tomada", "Foto capturada correctamente. No olvide guardar los cambios.", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo capturar la imagen: " + e.getMessage());
        }
    }

    // Cerrar la cámara
    webcam.close();
    }

    @FXML
    private void OAbtnResert(ActionEvent event) {
    }

    private void actualizarIMC() {
        double peso = sp_Peso.getValue();
        int altura = sp_Altura.getValue();
        double imc = UtilHelper.calcularIMC(peso, altura);
        tfIMC.setText(String.format("%.2f", imc));
    }

    @FXML
    private void OAbtnsubirfoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Configurar filtros para imágenes
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Archivos de imagen", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostrar el diálogo de selección de archivo
        File selectedFile = fileChooser.showOpenDialog(btnSubirFoto.getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Cargar la imagen y mostrarla en el ImageView
                Image image = new Image(selectedFile.toURI().toString());
                ivFoto.setImage(image);

                // Guardar la referencia al archivo original como propiedad del ImageView
                ivFoto.getProperties().put("imageFile", selectedFile);

            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Error al cargar la imagen", "No se pudo cargar la imagen seleccionada.");
            }
        }
    }

    // Método auxiliar para obtener la extensión del archivo
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }

// Método auxiliar para mostrar alertas (sobrecargado)
    private void mostrarAlerta(String titulo, String mensaje) {
        mostrarAlerta(titulo, mensaje, Alert.AlertType.ERROR);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
