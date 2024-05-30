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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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

    private static final String COUNTRY_CODE = "PER";
    private static final String CURRENCY_CODE = "PEN";
    private static final String API_KEY = "ab254a10-ddc2-4d84-8f31-d3fab9d49520";
    private static final String SIGNATURE_KEY = "eDpehY%YPYgsoludCSZhu*WLdmKBWfAo";

    private SynapPayButton paymentWidget;
    private FrameLayout synapForm;
    private Button synapButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pasarela, container, false);

        synapForm = view.findViewById(R.id.contenedorTarjetas);
        synapForm.setVisibility(View.GONE);

        synapButton = view.findViewById(R.id.btnPagar);
        synapButton.setVisibility(View.GONE);
        synapButton.setOnClickListener(v -> paymentWidget.pay());

        Button startPayment = view.findViewById(R.id.btnContinuar);
        startPayment.setOnClickListener(v -> startPayment());

        return view;
    }

    private void startPayment() {
        synapForm.setVisibility(View.VISIBLE);
        synapButton.setVisibility(View.VISIBLE);

        this.paymentWidget = SynapPayButton.create(synapForm);

        SynapTheme theme = new SynapLightTheme();
        SynapPayButton.setTheme(theme);

        SynapPayButton.setEnvironment(SynapPayButton.Environment.SANDBOX);

        SynapTransaction transaction = this.buildTransaction();
        SynapAuthenticator authenticator = this.buildAuthenticator(transaction);

        SynapPayButton.setListener(event -> {
            switch (event) {
                case START_PAY:
                    synapButton.setVisibility(View.GONE);
                    break;
                case INVALID_CARD_FORM:
                    synapButton.setVisibility(View.VISIBLE);
                    break;
                case VALID_CARD_FORM:
                    break;
            }
        });

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
        synapButton.setVisibility(View.VISIBLE);
    }

    private void showMessage(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(requireContext());
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Handler looper = new Handler(requireActivity().getApplicationContext().getMainLooper());
                        looper.post(() -> {
                            synapForm.setVisibility(View.GONE);
                            synapButton.setVisibility(View.GONE);
                        });
                        dialog.cancel();
                    }
                }
        );

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void handleResponse(boolean success, String message) {
        Looper.prepare();
        if (success) {
            // Aquí puedes agregar el código que se ejecutará si el pago es exitoso.
            // Por ejemplo, podrías redirigir al usuario a una pantalla de agradecimiento.
            navigateToSuccessScreen();
        } else {
            // Aquí puedes agregar el código que se ejecutará si el pago falla.
            // Por ejemplo, podrías mostrar un mensaje de error personalizado o dar al usuario la opción de intentarlo de nuevo.
            navigateToFailureScreen();
        }
        showMessage(message);
        Looper.loop();
    }

    private void navigateToSuccessScreen() {
        // Aquí puedes agregar el código para navegar a la pantalla de éxito.
        // Por ejemplo, si estás usando Navigation Component, puedes usar NavController para navegar a la pantalla de éxito.
    }

    private void navigateToFailureScreen() {
        // Aquí puedes agregar el código para navegar a la pantalla de fracaso.
        // Por ejemplo, si estás usando Navigation Component, puedes usar NavController para navegar a la pantalla de fracaso.
    }

    private SynapTransaction buildTransaction() {
        // Genere el número de orden, este es solo un ejemplo
        String number = String.valueOf(System.currentTimeMillis());

        // Seteo de los datos de transacción
        // Referencie al objeto país
        SynapCountry country = new SynapCountry();
        // Seteo del código de país
        country.setCode("PER");

        // Referencie al objeto moneda
        SynapCurrency currency = new SynapCurrency();
        // Seteo del código de moneda
        currency.setCode("PEN");

        //Seteo del monto
        String amount = "1.00";

        // Referencie al objeto cliente
        SynapPerson customer = new SynapPerson();
        // Seteo del cliente
        customer.setName("Javier");
        customer.setLastName("Pérez");

        // Referencie al objeto dirección del cliente
        SynapAddress address = new SynapAddress();
        // Seteo del pais (country), niveles de ubicación geográfica (levels), dirección (line1 y line2) y código postal (zip)
        address.setCountry("PER");
        address.setLevels(new ArrayList<String>());
        address.getLevels().add("150000");
        address.getLevels().add("150100");
        address.getLevels().add("150101");
        address.setLine1("Ca Carlos Ferreyros 180");
        address.setZip("15036");
        customer.setAddress(address);

        // Seteo del email y teléfono
        customer.setEmail("javier.perez@synapsolutions.com");
        customer.setPhone("999888777");

        // Referencie al objeto documento del cliente
        SynapDocument document = new SynapDocument();
        // Seteo del tipo y número de documento
        document.setType("DNI");
        document.setNumber("44556677");
        customer.setDocument(document);

        // Seteo de los datos de envío
        SynapPerson shipping = customer;
        // Seteo de los datos de facturación
        SynapPerson billing = customer;

        // Referencie al objeto producto
        SynapProduct productItem = new SynapProduct();
        // Seteo de los datos de producto
        productItem.setCode("123");
        productItem.setName("Llavero");
        productItem.setQuantity("1");
        productItem.setUnitAmount("1.00");
        productItem.setAmount("1.00");

        // Referencie al objeto lista de producto
        List<SynapProduct> products = new ArrayList<>();
        // Seteo de los datos de lista de producto
        products.add(productItem);

        // Referencie al objeto metadata
        SynapMetadata metadataItem = new SynapMetadata();
        // Seteo de los datos de metadata
        metadataItem.setName("nombre1");
        metadataItem.setValue("valor1");

        // Referencie al objeto lista de metadata
        List<SynapMetadata> metadataList = new ArrayList<>();
        // Seteo de los datos de lista de metadata
        metadataList.add(metadataItem);

        // Referencie al objeto orden
        SynapOrder order = new SynapOrder();
        // Seteo de los datos de orden
        order.setNumber(number);
        order.setAmount(amount);
        order.setCountry(country);
        order.setCurrency(currency);
        order.setProducts(products);
        order.setCustomer(customer);
        order.setShipping(shipping);
        order.setBilling(billing);
        order.setMetadata(metadataList);

        // Referencie al objeto configuración
        SynapSettings settings = new SynapSettings();
        // Seteo de los datos de configuración
        settings.setBrands(Arrays.asList(new String[]{"VISA", "MSCD", "AMEX", "DINC"}));
        settings.setLanguage("es_PE");
        settings.setBusinessService("MOB");

        // Referencie al objeto transacción
        SynapTransaction transaction = new SynapTransaction();
        // Seteo de los datos de transacción
        transaction.setOrder(order);
        transaction.setSettings(settings);

        // Feature Card-Storage (Recordar Tarjeta)
        SynapFeatures features = new SynapFeatures();
        SynapCardStorage cardStorage = new SynapCardStorage();

        // Omitir setUserIdentifier, si se trata de usuario anónimo
        cardStorage.setUserIdentifier("javier.perez@synapsolutions.com");

        features.setCardStorage(cardStorage);
        transaction.setFeatures(features);

        return transaction;
    }

    private SynapAuthenticator buildAuthenticator(SynapTransaction transaction) {
        String apiKey = "ab254a10-ddc2-4d84-8f31-d3fab9d49520";

        // La signatureKey y la función de generación de firma debe usarse e implementarse en el servidor del comercio utilizando la función criptográfica SHA-512
        // solo con propósito de demostrar la funcionalidad, se implementará en el ejemplo
        // (bajo ninguna circunstancia debe exponerse la signatureKey y la función de firma desde la aplicación porque compromete la seguridad)
        String signatureKey = "eDpehY%YPYgsoludCSZhu*WLdmKBWfAo";

        String signature = generateSignature(transaction, apiKey, signatureKey);

        // El campo onBehalf es opcional y se usa cuando un comercio agrupa otros sub comercios
        // la conexión con cada sub comercio se realiza con las credenciales del comercio agrupador
        // y enviando el identificador del sub comercio en el campo onBehalf
        //String onBehalf="cf747220-b471-4439-9130-d086d4ca83d4";

        // Referencie el objeto de autenticación
        SynapAuthenticator authenticator = new SynapAuthenticator();

        // Seteo de identificador del comercio (apiKey)
        authenticator.setIdentifier(apiKey);

        // Seteo de firma, que permite verificar la integridad de la transacción
        authenticator.setSignature(signature);

        // Seteo de identificador de sub comercio (solo si es un subcomercio)
        //authenticator.setOnBehalf(onBehalf);

        return authenticator;
    }

    // La signatureKey y la función de generación de firma debe usarse e implementarse en el servidor del comercio utilizando la función criptográfica SHA-512
// solo con propósito de demostrar la funcionalidad, se implementará en el ejemplo
// (bajo ninguna circunstancia debe exponerse la signatureKey y la función de firma desde la aplicación porque compromete la seguridad)
    private String generateSignature(SynapTransaction transaction, String apiKey, String signatureKey) {
        String orderNumber = transaction.getOrder().getNumber();
        String currencyCode = transaction.getOrder().getCurrency().getCode();
        String amount = transaction.getOrder().getAmount();

        String rawSignature = apiKey + orderNumber + currencyCode + amount + signatureKey;
        String signature = sha512Hex(rawSignature);
        return signature;
    }

    private String sha512Hex(String value) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(value.getBytes(StandardCharsets.UTF_8));
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
        } catch (Exception e) {
            // Handle the exception in a way that makes sense for your application
            throw new RuntimeException("Error generating SHA-512 hash", e);
        }
        return sb.toString();
    }
}