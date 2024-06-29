import { ColorModeContext, useMode } from './theme';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { Route, Routes } from 'react-router-dom';
import Topbar from './scenes/global/Topbar';
import Sidebar from './scenes/global/Sidebar';
import Dashboard from "./scenes/dashboard";

import Customers from "./scenes/customers";
import Employees from "./scenes/employees";
// import Offices from "./scenes/offices";
// import OrderDetails from "./scenes/orderdetails";
// import Orders from "./scenes/orders";
// import Payments from "./scenes/payments";
// import ProductLines from "./scenes/productlines";
// import Products from "./scenes/products";

import Form from './scenes/form';
import Calendar from "./scenes/calendar";
import Bar from "./scenes/bar"
import Pie from "./scenes/pie";
import Line from "./scenes/line";
import Geography from "./scenes/geography";

function App() {
    const [theme, colorMode] = useMode();

    return (
        <ColorModeContext.Provider value={colorMode}>
            <ThemeProvider theme={theme}>
                <CssBaseline />
                <div className="app">
                    <Sidebar />
                    <main className="content">
                        <Topbar />
                        <Routes>
                            <Route path="/" element={<Dashboard />} />

                            <Route path="/customers" element={<Customers />} />
                            <Route path="/employees" element={<Employees />} />
                            {/* <Route path="/offices" element={<Offices />} /> */}
                            {/* <Route path="/orderdetails" element={<OrderDetails />} /> */}
                            {/* <Route path="/orders" element={<Orders />} /> */}
                            {/* <Route path="/payments" element={<Payments />} /> */}
                            {/* <Route path="/productlines" element={<ProductLines />} /> */}
                            {/* <Route path="/products" element={<Products />} /> */}
                            {/**/}
                            <Route path="/form" element={<Form />} />
                            <Route path="/calendar" element={<Calendar />} />
                            <Route path="/bar" element={<Bar />} />
                            <Route path="/pie" element={<Pie />} />
                            <Route path="/line" element={<Line />} />
                            <Route path="/geography" element={<Geography />} />

                        </Routes>
                    </main>
                </div>
            </ThemeProvider>
        </ColorModeContext.Provider>
    )
}

export default App
