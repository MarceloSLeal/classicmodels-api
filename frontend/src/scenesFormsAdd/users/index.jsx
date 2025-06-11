
import React, { useState } from "react";

import { Box, Button, FormControl, Select, TextField, InputLabel, MenuItem } from "@mui/material";
import { Formik } from "formik";
import * as yup from "yup";
import useMediaQuery from "@mui/material/useMediaQuery";
import Header from "../../components/Header";
import PostForms from "../../components/formsRequests/PostForms";
import { Urls } from "../../api/Paths";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog"

const initialValues = {
  email: "",
  password: "",
  passwordConfirm: "",
  role: "",
}

const userSchema = yup.object().shape({
  email: yup.string().email().max(50).required("required"),
  password: yup.string().max(10).required("required"),
  passwordConfirm: yup.string().oneOf([yup.ref("password"), null], "Passwords must match"),
  role: yup.string().required("required"),
});

const FormAddUser = () => {
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [resetFormFn, setResetFormFn] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const url = Urls();

  const handleFormSubmit = async (values, { setSubmitting, resetForm }) => {
    const { passwordConfirm, ...user } = values;

    setStatus('');
    setResponseCode(null);

    try {

      const response = await PostForms(values, url.auth.register_Post);
      const data = await response.json();

      setResponseCode(response.status);

      if (response.ok) {
        setStatus("User created successfully");
        setResetFormFn(() => resetForm);
      } else {
        setStatus(`Error if: ${data.title || 'Failed to Create User'} - ${data.detail || ''}`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to create User'}`);
    }

    setSubmitting(false);
    setDialogOpen(true);
  }

  const handleClose = () => {
    setDialogOpen(false);
    if (responseCode === 200 && resetFormFn) {
      resetFormFn();
    }
  }

  return <Box m="20px">
    <Header title="CREATE USER" subtitle="Create a new User Profile" />

    <Formik
      onSubmit={handleFormSubmit}
      initialValues={initialValues}
      validationSchema={userSchema}
    >
      {({ values, errors, touched, handleBlur, handleChange, handleSubmit,
        setFieldValue }) => (
        <form onSubmit={handleSubmit}>
          <Box
            display="grid"
            gap="30px"
            gridTemplateColumns="repeat(5, minmax(0, 1fr))"
            sx={{
              "& > div": { gridColumn: isNonMobile ? undefined : "span 5" },
            }}
          >
            <TextField
              fullWidth
              variant="filled"
              type="text"
              label="Email"
              onBlur={handleBlur}
              onChange={handleChange}
              value={values.email}
              name="email"
              error={!!touched.email && !!errors.email}
              helperText={touched.email && errors.email}
              sx={{ gridColumn: "span 2" }}
            />

            <Box sx={{ gridColumn: "span 3" }} />

            <TextField
              fullWidth
              variant="filled"
              type="password"
              label="Password"
              onBlur={handleBlur}
              onChange={handleChange}
              value={values.password}
              name="password"
              error={!!touched.password && !!errors.password}
              helperText={touched.password && errors.password}
              sx={{ gridColumn: "span 1" }}
            />

            <TextField
              fullWidth
              variant="filled"
              type="password"
              label="Confirm Password"
              onBlur={handleBlur}
              onChange={handleChange}
              value={values.passwordConfirm}
              name="passwordConfirm"
              error={!!touched.passwordConfirm && !!errors.passwordConfirm}
              helperText={touched.passwordConfirm && errors.passwordConfirm}
              sx={{ gridColumn: "span 1" }}
            />

            <Box sx={{ gridColumn: "span 3" }} />

            <FormControl
              variant="filled"
              sx={{ gridColumn: "span 2" }}
            >
              <InputLabel id="user-select-role">Role </InputLabel>
              <Select
                labelId="user-select-role"
                id="user-role"
                name="role"
                value={values.role}
                onChange={(event) => setFieldValue('role', event.target.value)}
                onBlur={handleBlur}
                label="User Role"
              >
                <MenuItem key="USER" value="USER" >USER</MenuItem>
                <MenuItem key="ADMIN" value="ADMIN" >ADMIN</MenuItem>
              </Select>
            </FormControl>


          </Box>
          <Box display="flex" justifyContent="start" mt="20px">
            <Button type="submit" color="secondary" variant="contained">
              Create New user
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
}

export default FormAddUser;
