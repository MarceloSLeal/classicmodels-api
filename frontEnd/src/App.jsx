import { ColorModeContext, useMode } from './theme';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { Route, Routes } from 'react-router-dom';

import { AuthProvider, useAuth } from './auth/AuthContext.jsx';
import ProtectedRoute from './auth/ProtectedRoute';

import Topbar from './scenes/global/Topbar';
import Sidebar from './scenes/global/Sidebar';
import Dashboard from "./scenes/dashboard";
import Login from "./scenes/login/";

import Customers from "./scenes/customers";
import Employees from "./scenes/employees";
import Offices from './scenes/offices';
import OrderDetails from './scenes/orderDetails';
import Orders from './scenes/orders';
import Payments from './scenes/payments';
import ProductLines from './scenes/productLines';

import SelectOrderId from './scenesSelects/selectOrderId';
import SelectProductId from './scenesSelects/selectProductId';

import FormAddCustomer from './scenesFormsAdd/customers';
import FormEditCustomer from './scenesFormsEdit/customers';
import FormAddEmployee from './scenesFormsAdd/employees';
import FormEditEmployee from './scenesFormsEdit/employees';
import FormAddOffices from './scenesFormsAdd/offices';
import FormEditOffices from './scenesFormsEdit/offices';
import FormAddOrders from './scenesFormsAdd/orders';
import FormEditOrders from './scenesFormsEdit/orders';
import FormAddPayments from './scenesFormsAdd/payments';
import FormAddProductLines from './scenesFormsAdd/productlines';
import FormEditProductLines from './scenesFormsEdit/productLines';

import Form from './scenes/form';
import Calendar from "./scenes/calendar";
import Bar from "./scenes/bar"
import Pie from "./scenes/pie";
import Line from "./scenes/line";
import Geography from "./scenes/geography";

const App = () => {
  const [theme, colorMode] = useMode();

  return (
    <ColorModeContext.Provider value={colorMode}>
      <ThemeProvider theme={theme}>
        <CssBaseline />

        <AuthProvider>
          <div className="app">

            <Layout />

          </div>
        </AuthProvider>

      </ThemeProvider>
    </ColorModeContext.Provider>
  )
}

const Layout = () => {
  const { isAuthenticated } = useAuth();

  return (
    <>
      {isAuthenticated && <Sidebar />}
      <main className="content">
        {isAuthenticated && <Topbar />}
        <Routes>

          {/* Rota pública para login */}
          <Route path="/login" element={<Login />} />

          <Route path="/login" element={<Login />} />

          {/* Rotas protegidas */}
          <Route
            path="/*"
            element={
              <ProtectedRoute>
                <Routes>

                  {/* Outras rotas protegidas */}
                  <Route path="/" element={<Dashboard />} />

                  <Route path="/customers" element={<Customers />} />
                  <Route path="/employees" element={<Employees />} />
                  <Route path="/offices" element={<Offices />} />
                  <Route path="/orderdetails" element={<OrderDetails />} />
                  <Route path="/orders" element={<Orders />} />
                  <Route path="/payments" element={<Payments />} />
                  <Route path="/productlines" element={<ProductLines />} />

                  <Route path="/selectorderid" element={<SelectOrderId />} />
                  <Route path="/selectproductid" element={<SelectProductId />} />

                  <Route path="/formaddcustomer" element={<FormAddCustomer />} />
                  <Route path="/formeditcustomer" element={<FormEditCustomer />} />
                  <Route path="/formaddemployee" element={<FormAddEmployee />} />
                  <Route path="/formeditemployee" element={<FormEditEmployee />} />
                  <Route path="/formaddoffices" element={<FormAddOffices />} />
                  <Route path="/formeditoffices" element={<FormEditOffices />} />
                  <Route path="/formaddorders" element={<FormAddOrders />} />
                  <Route path="/formeditorders" element={<FormEditOrders />} />
                  <Route path="/formaddpayments" element={<FormAddPayments />} />
                  <Route path="/formaddproductlines" element={<FormAddProductLines />} />
                  <Route path="/formeditproductlines" element={<FormEditProductLines />} />

                  <Route path="/form" element={<Form />} />
                  <Route path="/calendar" element={<Calendar />} />
                  <Route path="/bar" element={<Bar />} />
                  <Route path="/pie" element={<Pie />} />
                  <Route path="/line" element={<Line />} />
                  <Route path="/geography" element={<Geography />} />
                </Routes>
              </ProtectedRoute>
            }
          />
        </Routes>
      </main>
    </>
  );
};

export default App;
