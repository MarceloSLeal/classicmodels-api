import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import { Box, Button, TextField } from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";
import { Formik } from "formik";
import * as yup from "yup";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog";

import { useAuth } from "../../auth/AuthContext";

import RefreshToken from "../auth/RefreshToken";

const Login = () => {
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const url = import.meta.env.VITE_URL_PREFIX;
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const navigateDashBoard = useNavigate();
  const userLogin = useAuth();

  const initialValues = {
    login: "", password: "",
  };

  const loginSchema = yup.object().shape({
    login: yup.string().max(50).required(),
    password: yup.string().max(50).required(),
  })

  const handleLogin = async (values) => {

    const response = await fetch(url + "auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },

      body: JSON.stringify(values),
    });

    if (!response.ok) {
      setStatus("Invalid username or password");
      setDialogOpen(true);
      setResponseCode(response.status);
      return;
    }

    const data = await response.json();

    userLogin.login(data.token);

    localStorage.setItem("token", data.token);
    localStorage.setItem("user", data.user);
    localStorage.setItem("role", data.role);
    localStorage.setItem("expires", new Date(data.expires).getTime());

    console.log("Token:", data.token);
    console.log("User:", data.user);
    console.log("role", data.role);
    console.log("Expires:", data.expires);

    navigateDashBoard("/");

  }

  const handleClose = () => {
    setDialogOpen(false);
  }

  const handleTeste = () => {
    console.log("handleTeste");
    RefreshToken();
  };


  return (
    <Box
      display="flex"
      flexDirection="column"
      justifyContent="center"
      alignItems="center"
      minHeight="100vh"
      p={2}
      bgcolor="background.default"
    >

      <Formik
        onSubmit={handleLogin}
        initialValues={initialValues}
        validationSchema={loginSchema}
      >
        {({ values, handleChange, handleBlur, handleSubmit, errors, touched }) => (


          <form onSubmit={handleSubmit} style={{ width: isNonMobile ? "400px" : "100%" }}>
            <Box mb={3} textAlign="center">
              <h1>Login</h1>
            </Box>

            <Box display="flex" flexDirection="column" gap="20px">
              <TextField
                variant="filled"
                type="text"
                label="Login"
                placeholder="example@email.com"
                value={values.login}
                name="login"
                onChange={handleChange}
                onBlur={handleBlur}
                error={touched.login && Boolean(errors.login)}
                helperText={touched.login && errors.login}
                fullWidth
              />
              <TextField
                variant="filled"
                type="password"
                label="Password"
                value={values.password}
                name="password"
                onChange={handleChange}
                onBlur={handleBlur}
                error={touched.password && Boolean(errors.password)}
                helperText={touched.password && errors.password}
                fullWidth
              />
            </Box>

            <Box mt={4}>
              <Button
                type="submit"
                fullWidth
                color="secondary"
                variant="contained"
              >
                Login
              </Button>

              <Button
                onClick={handleTeste}
                fullWidth
                color="secondary"
                variant="contained"
              >
                Teste
              </Button>
            </Box>
          </form>

        )}
      </Formik>

      <OperationStatusDialog
        dialogOpen={dialogOpen} onClose={handleClose} status={status}
        responseCode={responseCode} onClick={handleClose}
      />

    </Box>
  );

}

export default Login;
