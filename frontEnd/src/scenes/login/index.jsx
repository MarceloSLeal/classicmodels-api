import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import { IconButton, InputAdornment } from '@mui/material';
import { Visibility, VisibilityOff } from '@mui/icons-material';

import { Box, Button, TextField } from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";
import { Formik } from "formik";
import * as yup from "yup";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog";
import { useAuth } from "../../auth/AuthContext";
import PostForms from "../../components/formsRequests/PostForms";
import { Urls } from "../../api/Paths";

const Login = () => {
  const isNonMobile = useMediaQuery("(min-width:600px)");
  // const url = import.meta.env.VITE_URL_PREFIX;
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const navigateDashBoard = useNavigate();
  const userLogin = useAuth();
  const url = Urls();

  const [showPassword, setShowPassword] = useState(false);

  const initialValues = {
    email: "", password: "",
  };

  const loginSchema = yup.object().shape({
    email: yup.string().email().max(50).required(),
    password: yup.string().max(50).required(),
  })

  const handleLogin = async (values) => {

    const response = await PostForms(values, url.auth.login_Post);
    const data = await response.json();

    if (!response.ok) {
      setDialogOpen(true);
      setResponseCode(response.status);
      setStatus("Invalid email or password");
      return;
    }

    userLogin.login();

    localStorage.setItem("user", data.email);
    localStorage.setItem("role", data.role);

    navigateDashBoard("/");
  }

  const handleClose = () => {
    setDialogOpen(false);
  }

  const handleTogglePassword = () => {
    setShowPassword((prev) => !prev);
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
                label="Email"
                placeholder="example@email.com"
                value={values.email}
                name="email"
                onChange={handleChange}
                onBlur={handleBlur}
                error={touched.email && Boolean(errors.email)}
                helperText={touched.email && errors.email}
                fullWidth
              />
              <TextField
                variant="filled"
                type={showPassword ? "text" : "password"}

                label="Password"
                value={values.password}
                name="password"
                onChange={handleChange}
                onBlur={handleBlur}
                error={touched.password && Boolean(errors.password)}
                helperText={touched.password && errors.password}
                fullWidth

                InputProps={{
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        onClick={handleTogglePassword}
                        onMouseDown={(e) => e.preventDefault()}
                        edge="end"
                      >
                        {showPassword ? <VisibilityOff /> : <Visibility />}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
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
