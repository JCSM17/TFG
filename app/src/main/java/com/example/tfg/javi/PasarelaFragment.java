package com.example.tfg.javi;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.R;
import com.synap.pay.SynapPayButton;
import com.synap.pay.handler.payment.SynapAuthorizeHandler;
import com.synap.pay.model.payment.SynapAddress;
import com.synap.pay.model.payment.SynapCardStorage;
import com.synap.pay.model.payment.SynapCountry;
import com.synap.pay.model.payment.SynapCurrency;
import com.synap.pay.model.payment.SynapDocument;
import com.synap.pay.model.payment.SynapFeatures;
import com.synap.pay.model.payment.SynapMetadata;
import com.synap.pay.model.payment.SynapOrder;
import com.synap.pay.model.payment.SynapPerson;
import com.synap.pay.model.payment.SynapProduct;
import com.synap.pay.model.payment.SynapSettings;
import com.synap.pay.model.payment.SynapTransaction;
import com.synap.pay.model.payment.response.SynapAuthorizeResponse;
import com.synap.pay.model.security.SynapAuthenticator;
import com.synap.pay.theming.SynapLightTheme;
import com.synap.pay.theming.SynapTheme;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Contraseñas que hay que meter               // Contraseñas que hay que meter
// Marca: Visa                                 // Marca: Mastercard
// Numero tarjeta: 4242 4242 4242 4242         // Numero tarjeta: 5555 5555 5555 4444
// Vencimiento: 12/24                          // Vencimiento: 12/24
// CVV: 123                                    // CVV: 123

public class PasarelaFragment extends Fragment {

    // Constantes para el código de país, código de moneda, clave API y clave de firma
    private static final String COUNTRY_CODE = "PER";
    private static final String CURRENCY_CODE = "PEN";
    private static final String API_KEY = "ab254a10-ddc2-4d84-8f31-d3fab9d49520";
    private static final String SIGNATURE_KEY = "eDpehY%YPYgsoludCSZhu*WLdmKBWfAo";

    private SynapPayButton paymentWidget;
    private FrameLayout synapForm;
    private Button synapButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        try {
            // Infla el diseño de la interfaz de usuario del fragmento
            view = inflater.inflate(R.layout.fragment_pasarela, container, false);

            synapForm = view.findViewById(R.id.contenedorTarjetas);
            if (synapForm == null) {
                // Maneja el caso en el que synapForm es nulo
                Toast.makeText(getContext(), "Error: synapForm es null.", Toast.LENGTH_SHORT).show();
                return view;
            }
            synapForm.setVisibility(View.GONE);

            synapButton = view.findViewById(R.id.btnPagar);
            synapButton.setVisibility(View.GONE);

            // Inicializa el objeto paymentWidget aquí
            this.paymentWidget = SynapPayButton.create(synapForm);

            // Configura el botón de pago para iniciar el pago al hacer clic
            synapButton.setOnClickListener(v -> {
                try {
                    if (paymentWidget != null) {
                        paymentWidget.pay();
                    } else {
                        // Maneja el caso en el que paymentWidget es nulo
                        Toast.makeText(getContext(), "Error: El botón de pago no está inicializado.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e) {
                    // Maneja la excepción aquí
                    Toast.makeText(getContext(), "Error: Se produjo un error inesperado.", Toast.LENGTH_SHORT).show();
                }
            });

            // Configura el botón de "Continuar" para iniciar el proceso de pago
            Button startPayment = view.findViewById(R.id.btnContinuar);
            startPayment.setOnClickListener(v -> startPayment());

            // Configura el botón de "Finalizar" para navegar a la pantalla de éxito
            Button btnFinalizar = view.findViewById(R.id.btnFinalizar);
            btnFinalizar.setOnClickListener(v -> navigateToSuccessScreen());
        } catch (NullPointerException e) {
            // Maneja la excepción aquí si ocurre algún error durante la creación de la vista
            Toast.makeText(getContext(), "Error: Se produjo un error inesperado.", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializa el objeto paymentWidget en onViewCreated para asegurar que esté listo
        this.paymentWidget = SynapPayButton.create(synapForm);
    }

    // Método para iniciar el proceso de pago mostrando el formulario y el botón de pago
    private void startPayment() {
        synapForm.setVisibility(View.VISIBLE);
        synapButton.setVisibility(View.VISIBLE);

        // Re-inicializa el objeto paymentWidget al iniciar el pago
        this.paymentWidget = SynapPayButton.create(synapForm);

        // Configura el tema de la interfaz de usuario del widget de pago
        SynapTheme theme = new SynapLightTheme();
        SynapPayButton.setTheme(theme);

        // Configura el ambiente del widget de pago (en este caso, sandbox para pruebas)
        SynapPayButton.setEnvironment(SynapPayButton.Environment.SANDBOX);

        // Construye la transacción para enviar al servidor de pago
        SynapTransaction transaction = this.buildTransaction();
        SynapAuthenticator authenticator = this.buildAuthenticator(transaction);

        // Configura el listener para manejar los eventos del widget de pago
        SynapPayButton.setListener(event -> {
            switch (event) {
                case START_PAY:
                    synapButton.setVisibility(View.GONE); // Oculta el botón de pago al iniciar el proceso
                    break;
                case INVALID_CARD_FORM:
                    synapButton.setVisibility(View.VISIBLE); // Muestra el botón de pago si el formulario es inválido
                    break;
                case VALID_CARD_FORM:
                    // No se necesita ninguna acción específica para un formulario válido
                    break;
            }
        });

        // Configura el widget de pago con la autenticación, transacción y handler de autorización
        this.paymentWidget.configure(
                authenticator,
                transaction,
                new SynapAuthorizeHandler() {
                    @Override
                    public void success(SynapAuthorizeResponse response) {
                        handleResponse(response.getResult().getAccepted(), response.getResult().getMessage());
                    }

                    @Override
                    public void failed(SynapAuthorizeResponse response) {
                        handleResponse(false, response.getMessage().getText());
                    }
                }
        );

        synapButton.setVisibility(View.VISIBLE); // Asegura que el botón de pago esté visible
    }

    // Método para mostrar un mensaje en un cuadro de diálogo
    private void showMessage(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(requireContext());
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Usa un Handler para asegurar que la interfaz de usuario se actualice correctamente
                        Handler looper = new Handler(requireActivity().getApplicationContext().getMainLooper());
                        looper.post(() -> {
                            synapForm.setVisibility(View.GONE); // Oculta el formulario de pago
                            synapButton.setVisibility(View.GONE); // Oculta el botón de pago
                        });
                        dialog.cancel();
                    }
                }
        );

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    // Método para manejar la respuesta del servidor después de realizar el pago
    private void handleResponse(boolean success, String message) {
        Looper.prepare(); // Prepara un Looper para mostrar un mensaje de manera sincronizada

        if (success) {
            // Realiza acciones adicionales si el pago fue exitoso
            navigateToSuccessScreen(); // Navega a la pantalla de éxito
        } else {
            // Realiza acciones si el pago falló
            navigateToFailureScreen(); // Muestra un mensaje de error
        }
        showMessage(message); // Muestra el mensaje recibido del servidor

        Looper.loop(); // Inicia el bucle del Looper para mostrar el mensaje correctamente
    }

    // Método para navegar a la pantalla de confirmación de suscripción
    private void navigateToSuccessScreen() {
        SuscripcionConfirmFragment suscripcionConfirmFragment = new SuscripcionConfirmFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, suscripcionConfirmFragment);
            fragmentTransaction.commit();
        }
    }

    // Método para navegar a la pantalla de fallo en el pago
    private void navigateToFailureScreen() {
        // Muestra un mensaje de error en un Toast
        Toast.makeText(getContext(), "Error en el pago, por favor intenta de nuevo.", Toast.LENGTH_SHORT).show();
    }

    // Método para construir y configurar los detalles de la transacción
    private SynapTransaction buildTransaction() {
        // Genera un número de orden único, en este caso se usa el tiempo actual
        String number = String.valueOf(System.currentTimeMillis());

        // Configura los datos de la transacción, incluyendo país, moneda, monto y detalles del cliente
        SynapCountry country = new SynapCountry();
        country.setCode("PER");

        SynapCurrency currency = new SynapCurrency();
        currency.setCode("PEN");

        String amount = "1.00";

        SynapPerson customer = new SynapPerson();
        customer.setName("Javier");
        customer.setLastName("Pérez");

        SynapAddress address = new SynapAddress();
        address.setCountry("PER");
        address.setLevels(new ArrayList<String>());
        address.getLevels().add("150000");
        address.getLevels().add("150100");
        address.getLevels().add("150101");
        address.setLine1("Ca Carlos Ferreyros 180");
        address.setZip("15036");
        customer.setAddress(address);

        customer.setEmail("javier.perez@synapsolutions.com");
        customer.setPhone("999888777");

        SynapDocument document = new SynapDocument();
        document.setType("DNI");
        document.setNumber("44556677");
        customer.setDocument(document);

        SynapPerson shipping = customer;
        SynapPerson billing = customer;

        SynapProduct productItem = new SynapProduct();
        productItem.setCode("123");
        productItem.setName("Llavero");
        productItem.setQuantity("1");
        productItem.setUnitAmount("1.00");
        productItem.setAmount("1.00");

        List<SynapProduct> products = new ArrayList<>();
        products.add(productItem);

        SynapMetadata metadataItem = new SynapMetadata();
        metadataItem.setName("nombre1");
        metadataItem.setValue("valor1");

        List<SynapMetadata> metadataList = new ArrayList<>();
        metadataList.add(metadataItem);

        SynapOrder order = new SynapOrder();
        order.setNumber(number);
        order.setAmount(amount);
        order.setCountry(country);
        order.setCurrency(currency);
        order.setProducts(products);
        order.setCustomer(customer);
        order.setShipping(shipping);
        order.setBilling(billing);
        order.setMetadata(metadataList);

        SynapSettings settings = new SynapSettings();
        settings.setBrands(Arrays.asList(new String[]{"VISA", "MSCD", "AMEX", "DINC"}));
        settings.setLanguage("es_PE");
        settings.setBusinessService("MOB");

        SynapTransaction transaction = new SynapTransaction();
        transaction.setOrder(order);
        transaction.setSettings(settings);

        SynapFeatures features = new SynapFeatures();
        SynapCardStorage cardStorage = new SynapCardStorage();
        cardStorage.setUserIdentifier("javier.perez@synapsolutions.com");
        features.setCardStorage(cardStorage);

        transaction.setFeatures(features);

        return transaction;
    }

    // Método para construir el objeto SynapAuthenticator para autenticar la transacción
    private SynapAuthenticator buildAuthenticator(SynapTransaction transaction) {
        String apiKey = "ab254a10-ddc2-4d84-8f31-d3fab9d49520";
        String signatureKey = "eDpehY%YPYgsoludCSZhu*WLdmKBWfAo";

        String signature = generateSignature(transaction, apiKey, signatureKey);

        SynapAuthenticator authenticator = new SynapAuthenticator();
        authenticator.setIdentifier(apiKey);
        authenticator.setSignature(signature);

        return authenticator;
    }

    // Método para generar la firma usando SHA-512
    private String generateSignature(SynapTransaction transaction, String apiKey, String signatureKey) {
        String orderNumber = transaction.getOrder().getNumber();
        String currencyCode = transaction.getOrder().getCurrency().getCode();
        String amount = transaction.getOrder().getAmount();

        String rawSignature = apiKey + orderNumber + currencyCode + amount + signatureKey;
        String signature = sha512Hex(rawSignature);
        return signature;
    }

    // Método para generar un hash SHA-512.
    private String sha512Hex(String value) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(value.getBytes(StandardCharsets.UTF_8));
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
        } catch (Exception e) {
            // Maneja la excepción de forma adecuada para tu aplicación
            throw new RuntimeException("Error generando hash SHA-512", e);
        }
        return sb.toString();
    }
}