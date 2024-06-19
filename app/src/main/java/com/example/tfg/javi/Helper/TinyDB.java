package com.example.tfg.javi.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.tfg.javi.domain.PopularDomain;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class TinyDB {
    private SharedPreferences preferences; // Almacena las preferencias de la aplicación
    private String DEFAULT_APP_IMAGEDATA_DIRECTORY; // Directorio predeterminado para datos de imágenes
    private String lastImagePath = ""; // Última ruta de la imagen guardada

    public TinyDB(Context appContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext); // Inicializa las preferencias usando el contexto de la aplicación
    }

    // Método para obtener una imagen desde una ruta de archivo
    public Bitmap getImage(String path) {
        Bitmap bitmapFromPath = null;
        try {
            bitmapFromPath = BitmapFactory.decodeFile(path); // Decodifica el archivo de la ruta especificada

        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones en caso de error durante la decodificación
        }

        return bitmapFromPath; // Retorna la imagen decodificada o null si hay un error
    }

    // Método para obtener la última ruta de imagen guardada
    public String getSavedImagePath() {
        return lastImagePath;
    }

    // Método para guardar una imagen en un directorio específico
    public String putImage(String theFolder, String theImageName, Bitmap theBitmap) {
        if (theFolder == null || theImageName == null || theBitmap == null)
            return null; // Verifica que los parámetros no sean nulos

        this.DEFAULT_APP_IMAGEDATA_DIRECTORY = theFolder; // Establece el directorio predeterminado para los datos de imágenes
        String mFullPath = setupFullPath(theImageName); // Configura la ruta completa de la imagen

        if (!mFullPath.equals("")) {
            lastImagePath = mFullPath; // Actualiza la última ruta de imagen guardada
            saveBitmap(mFullPath, theBitmap); // Guarda el bitmap en la ruta especificada
        }

        return mFullPath; // Retorna la ruta completa donde se guardó la imagen
    }

    // Método para guardar una imagen en una ruta completa especificada
    public boolean putImageWithFullPath(String fullPath, Bitmap theBitmap) {
        return !(fullPath == null || theBitmap == null) && saveBitmap(fullPath, theBitmap); // Guarda el bitmap en la ruta completa especificada
    }

    // Método privado para configurar la ruta completa de una imagen
    private String setupFullPath(String imageName) {
        File mFolder = new File(Environment.getExternalStorageDirectory(), DEFAULT_APP_IMAGEDATA_DIRECTORY); // Crea una carpeta en el directorio externo si no existe

        if (isExternalStorageReadable() && isExternalStorageWritable() && !mFolder.exists()) {
            if (!mFolder.mkdirs()) {
                Log.e("ERROR", "Failed to setup folder"); // Muestra un mensaje de error si falla la creación de la carpeta
                return "";
            }
        }

        return mFolder.getPath() + '/' + imageName; // Retorna la ruta completa de la imagen
    }

    // Método privado para guardar un bitmap en una ruta específica
    private boolean saveBitmap(String fullPath, Bitmap bitmap) {
        if (fullPath == null || bitmap == null)
            return false; // Verifica que la ruta y el bitmap no sean nulos

        boolean fileCreated = false;
        boolean bitmapCompressed = false;
        boolean streamClosed = false;

        File imageFile = new File(fullPath); // Crea un archivo en la ruta completa especificada

        if (imageFile.exists())
            if (!imageFile.delete())
                return false; // Elimina el archivo existente si falla la eliminación, retorna falso

        try {
            fileCreated = imageFile.createNewFile(); // Crea un nuevo archivo para la imagen

        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones si falla la creación del archivo
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imageFile);
            bitmapCompressed = bitmap.compress(CompressFormat.PNG, 100, out); // Comprime el bitmap en formato PNG

        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones si falla la compresión del bitmap
            bitmapCompressed = false;

        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                    streamClosed = true;  // Cierra el flujo de salida correctamente

                } catch (IOException e) {
                    e.printStackTrace(); // Manejo de excepciones si falla el cierre del flujo de salida
                    streamClosed = false;
                }
            }
        }

        return (fileCreated && bitmapCompressed && streamClosed); // Retorna verdadero si todas las operaciones se realizaron con éxito
    }

    // Métodos para obtener valores de diferentes tipos desde las preferencias
    public int getInt(String key) {
        return preferences.getInt(key, 0); // Obtiene un entero desde las preferencias con un valor predeterminado de 0 si no existe
    }

    public ArrayList<Integer> getListInt(String key) {
        String[] myList = TextUtils.split(preferences.getString(key, ""), "‚‗‚"); // Obtiene una lista de enteros desde las preferencias
        ArrayList<String> arrayToList = new ArrayList<String>(Arrays.asList(myList));
        ArrayList<Integer> newList = new ArrayList<Integer>();

        for (String item : arrayToList)
            newList.add(Integer.parseInt(item)); // Convierte cada elemento de la lista de cadena a entero

        return newList; // Retorna la lista de enteros convertida
    }

    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }

    public float getFloat(String key) {
        return preferences.getFloat(key, 0);
    }

    public double getDouble(String key) {
        String number = getString(key);

        try {
            return Double.parseDouble(number);

        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public ArrayList<Double> getListDouble(String key) {
        String[] myList = TextUtils.split(preferences.getString(key, ""), "‚‗‚");
        ArrayList<String> arrayToList = new ArrayList<String>(Arrays.asList(myList));
        ArrayList<Double> newList = new ArrayList<Double>();

        for (String item : arrayToList)
            newList.add(Double.parseDouble(item));

        return newList;
    }

    public ArrayList<Long> getListLong(String key) {
        String[] myList = TextUtils.split(preferences.getString(key, ""), "‚‗‚");
        ArrayList<String> arrayToList = new ArrayList<String>(Arrays.asList(myList));
        ArrayList<Long> newList = new ArrayList<Long>();

        for (String item : arrayToList)
            newList.add(Long.parseLong(item));

        return newList;
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public ArrayList<String> getListString(String key) {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public ArrayList<Boolean> getListBoolean(String key) {
        ArrayList<String> myList = getListString(key);
        ArrayList<Boolean> newList = new ArrayList<Boolean>();

        for (String item : myList) {
            if (item.equals("true")) {
                newList.add(true);
            } else {
                newList.add(false);
            }
        }

        return newList; // Retorna la lista de enteros convertida
    }

    // Método para obtener una lista de objetos serializables desde las preferencias usando Gson
    public ArrayList<PopularDomain> getListObject(String key) {
        Gson gson = new Gson();

        ArrayList<String> objStrings = getListString(key); // Obtiene una lista de cadenas desde las preferencias
        ArrayList<PopularDomain> playerList = new ArrayList<PopularDomain>();

        for (String jObjString : objStrings) {
            PopularDomain player = gson.fromJson(jObjString, PopularDomain.class);
            playerList.add(player);
        }
        return playerList;
    }


    public Object getObject(String key, Class<?> classOfT) {
        String json = getString(key);
        Object value = new Gson().fromJson(json, classOfT);
        if (value == null)
            throw new NullPointerException();
        if (classOfT.isInstance(value)) {
            return value;
        } else {
            throw new ClassCastException("El objeto no es de tipo " + classOfT.getName());
        }
    }

    // Put methods
    public void putInt(String key, int value) {
        checkForNullKey(key);
        preferences.edit().putInt(key, value).apply();
    }

    public void putListInt(String key, ArrayList<Integer> intList) {
        checkForNullKey(key);
        Integer[] myIntList = intList.toArray(new Integer[intList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myIntList)).apply();
    }

    public void putLong(String key, long value) {
        checkForNullKey(key);
        preferences.edit().putLong(key, value).apply();
    }

    public void putListLong(String key, ArrayList<Long> longList) {
        checkForNullKey(key);
        Long[] myLongList = longList.toArray(new Long[longList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myLongList)).apply();
    }

    public void putFloat(String key, float value) {
        checkForNullKey(key);
        preferences.edit().putFloat(key, value).apply();
    }

    public void putDouble(String key, double value) {
        checkForNullKey(key);
        putString(key, String.valueOf(value));
    }

    public void putListDouble(String key, ArrayList<Double> doubleList) {
        checkForNullKey(key);
        Double[] myDoubleList = doubleList.toArray(new Double[doubleList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myDoubleList)).apply();
    }

    public void putString(String key, String value) {
        checkForNullKey(key);
        checkForNullValue(value);
        preferences.edit().putString(key, value).apply();
    }

    public void putListString(String key, ArrayList<String> stringList) {
        checkForNullKey(key);
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }

    public void putBoolean(String key, boolean value) {
        checkForNullKey(key);
        preferences.edit().putBoolean(key, value).apply();
    }

    public void putListBoolean(String key, ArrayList<Boolean> boolList) {
        checkForNullKey(key);
        ArrayList<String> newList = new ArrayList<String>();

        for (Boolean item : boolList) {
            if (item) {
                newList.add("true");
            } else {
                newList.add("false");
            }
        }

        putListString(key, newList);
    }

    public void putObject(String key, Object obj) {
        checkForNullKey(key);
        Gson gson = new Gson();
        putString(key, gson.toJson(obj));
    }

    public void putListObject(String key, ArrayList<PopularDomain> playerList) {
        checkForNullKey(key);
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<String>();
        for (PopularDomain player : playerList) {
            objStrings.add(gson.toJson(player));
        }
        putListString(key, objStrings);
    }

    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }

    public boolean deleteImage(String path) {
        return new File(path).delete();
    }

    public void clear() {
        preferences.edit().clear().apply();
    }

    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    public void registerOnSharedPreferenceChangeListener(
            SharedPreferences.OnSharedPreferenceChangeListener listener) {

        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(
            SharedPreferences.OnSharedPreferenceChangeListener listener) {

        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    private void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

    private void checkForNullValue(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
    }
}