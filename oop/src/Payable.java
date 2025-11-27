public interface Payable {
    // يحسب اجمالي السعر بناءً على عدد الأشخاص وسعر الرحلة
    double calculateTotalPrice();
  
    // ينفّذ عملية الدفع (مثلاً تغيير PaymentStatus)
    boolean processPayment(double amount);

}
