package util;

import javax.swing.JOptionPane;

public class Validador {

    // ── Campos de texto ──────────────────────────────────────────

    // Verifica que el input del JOptionPane no sea null (usuario canceló) ni vacío
    public static boolean esValido(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    // Pide un campo obligatorio y avisa si está vacío
    public static String pedirCampoObligatorio(String etiqueta) {
        String valor = JOptionPane.showInputDialog(etiqueta + ":");
        if (!esValido(valor)) {
            JOptionPane.showMessageDialog(null, etiqueta + " es obligatorio.");
            return null;
        }
        return valor.trim();
    }

    // ── Email ────────────────────────────────────────────────────

    public static boolean esEmailValido(String email) {
        if (!esValido(email)) return false;
        // Verifica formato básico: algo@algo.algo
        return email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
    }

    // ── DNI ──────────────────────────────────────────────────────

    public static boolean esDniValido(String dni) {
        if (!esValido(dni)) return false;
        // Solo números, entre 7 y 8 dígitos
        return dni.matches("^\\d{7,8}$");
    }

    // ── Teléfono ─────────────────────────────────────────────────

    public static boolean esTelefonoValido(String telefono) {
        if (!esValido(telefono)) return false;
        // Solo números, entre 8 y 15 dígitos (acepta código de área)
        return telefono.matches("^\\d{8,15}$");
    }

    // ── Contraseña ───────────────────────────────────────────────

    public static boolean esContraseniaValida(String contrasenia) {
        if (!esValido(contrasenia)) return false;
        // Mínimo 8 caracteres, al menos una letra y un número
        return contrasenia.length() >= 8
            && contrasenia.matches(".*[a-zA-Z].*")
            && contrasenia.matches(".*\\d.*");
    }

    // ── Fechas ───────────────────────────────────────────────────

    public static boolean esFechaValida(String fecha) {
        if (!esValido(fecha)) return false;
        // Formato AAAA-MM-DD
        return fecha.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");
    }

    // Verifica que la fecha sea hoy o en el futuro (para reservar turnos)
    public static boolean esFechaFutura(String fecha) {
        if (!esFechaValida(fecha)) return false;
        java.time.LocalDate fechaTurno = java.time.LocalDate.parse(fecha);
        return !fechaTurno.isBefore(java.time.LocalDate.now());
    }

    // ── Monto ────────────────────────────────────────────────────

    public static boolean esMontoValido(String monto) {
        if (!esValido(monto)) return false;
        try {
            double valor = Double.parseDouble(monto);
            return valor > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}