import { Route, Routes } from 'react-router-dom';
import { Layout } from './layouts/Layout/Layout';
import { HomePage } from './layouts/HomePage/HomePage';
import { PartsPage } from './layouts/PartsPage/PartsPage';
import { ProductPage } from './layouts/ProductPage/ProductPage';
import { CartPage } from './layouts/CartPage/CartPage';
import { CheckoutLayout } from './layouts/CheckoutLayout/CheckoutLayout';
import { AddressPage } from './layouts/CheckoutLayout/pages/AddressPage/AddressPage';
import { DeliveryPage } from './layouts/CheckoutLayout/pages/DeliveryPage/DeliveryPage';
import { PaymentPage } from './layouts/CheckoutLayout/pages/PaymentPage/PaymentPage';
import { ErrorPage } from './layouts/ErrorPage/ErrorPage';
import { useLocalStorage } from './hooks/useLocalStorage';
import { GuestPage } from './layouts/CheckoutLayout/pages/GuestPage/GuestPage';
import { VerifyEmail } from './layouts/VerifyEmail/VerifyEmail';
import { LoginPage } from './layouts/LoginPage/LoginPage';
import { AccountPage } from './layouts/AccountPage/AccountPage';
import { ForgotPasswordPage } from './layouts/ForgotPasswordPage/ForgotPasswordPage';
import { ChangePasswordPage } from './layouts/ChangePasswordPage/ChangePasswordPage';
import { useAuth } from './context/AuthContext';
import { WriteReviewPage } from './layouts/WriteReviewPage/WriteReviewPage';
import { AuthGuard } from './components/AuthGuard/AuthGuard';

function App() {
  const auth = useAuth()

  const [guestEmail, setGuestEmail] = useLocalStorage<string>("guest-email", "")

  return (
    <Routes>
      <Route path="/cart" element={<CartPage />} />
      <Route path="/checkout" element={<CheckoutLayout />} >
        <Route path="address" element={
          auth.isAuthenticated() || guestEmail ?
            <AddressPage />
            :
            <GuestPage setGuestEmail={setGuestEmail} />
        }
        />
        <Route path="delivery" element={<DeliveryPage />} />
        <Route path="payment" element={<PaymentPage />} />
      </Route>
      <Route path="login" element={<LoginPage />} />
      <Route path="/verify-email" element={<VerifyEmail />} />
      <Route path="/password/recover" element={<ForgotPasswordPage />} />
      <Route path="/change-password" element={<ChangePasswordPage />} />
      <Route path="/" element={<Layout />}>
        <Route index element={<HomePage />} />
        <Route path="/account" element={<AccountPage />} />
        <Route path="/products/:product" element={<ProductPage />} />
        <Route path="/products/:product/reviews/new" element={
          <AuthGuard component={WriteReviewPage} />
        } />
        <Route path="/:make/:model" element={<PartsPage />} />
        <Route path="/:make/:model/:category" element={<PartsPage />} />
        <Route path="*" element={<ErrorPage />} />
      </Route>
    </Routes>
  );
}

export default App;
