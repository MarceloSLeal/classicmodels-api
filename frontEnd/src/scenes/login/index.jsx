import React from "react";
import { Box, Button, TextField } from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";

const Login = () => {
  const isNonMobile = useMediaQuery("(min-width:600px)");

  const handleLogin = () => {
    // lógica de autenticação
  }

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
      <form onSubmit={handleLogin} style={{ width: isNonMobile ? "400px" : "100%" }}>
        <Box mb={3} textAlign="center">
          <h1>Login</h1>
        </Box>

        <Box display="flex" flexDirection="column" gap="20px">
          <TextField
            fullWidth
            variant="filled"
            type="text"
            label="Login"
            placeholder="example@email.com"
          />
          <TextField
            fullWidth
            variant="filled"
            type="password"
            label="Password"
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
    </Box>
  );

}

export default Login;
