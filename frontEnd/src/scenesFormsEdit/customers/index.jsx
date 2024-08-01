import { useLocation } from "react-router-dom";
import React, { useState } from "react";

import {
  Box, Button, Select, MenuItem, FormControl, InputLabel, Dialog,
  DialogActions, DialogContent, DialogContentText, DialogTitle,
} from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";

import { Formik } from "formik";
import * as yup from "yup";

import CustomersFormEditInputs from "../../components/formEditInputs/Customers";
import { useNavigate } from "react-router-dom";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";

const phoneRegExp = /^((\+[1-9]{1,4}[ -]?)|(\([0-9]{2,3}\)[ -]?)|([0-9]{2,4})[ -]?)*?[0-9]{3,4}[ -]?[0-9]{3,4}$/;
const customersSchema = yup.object().shape({
  name: yup.string().max(50).required(),
  email: yup.string().email().max(50).required(),
  contactLastName: yup.string().max(50).required(),
  contactFirstName: yup.string().max(50).required(),
  phone: yup
    .string()
    .matches(phoneRegExp, "Phone number is not valid")
    .required(),
  addressLine1: yup.string().max(50).required(),
  addressLine2: yup.string().max(50),
  city: yup.string().max(50).required(),
  state: yup.string().max(50),
  postalCode: yup.string().max(15),
  country: yup.string().max(50).required(),
  creditLimit: yup.number().positive().max(999999.99).required(),
  employeeId: yup.number().positive(),
});

const FormEditCustomer = () => {
  const location = useLocation();
  const { rowData, data } = location.state || {};
  //TODO criar um novo endpoint para pegar o código apenas dos employees vendedores
  const employeeIds = [...new Set(data.map(item => item.employeeId).filter(id => id !== null))];
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const navigate = useNavigate();

  const initialValues = {
    id: rowData.id, name: rowData.name, email: rowData.email, contactLastName:
      rowData.contactLastName, contactFirstName: rowData.contactFirstName,
    phone: rowData.phone, addressLine1: rowData.addressLine1, addressLine2:
      rowData.addressLine2, city: rowData.city, state: rowData.state,
    postalCode: rowData.postalCode, country: rowData.country, creditLimit:
      rowData.creditLimit, employeeId: rowData.employeeId
  };

  const handleFormSubmit = async (values, { setSubmitting }) => {
    const url = Urls(rowData.id);
    setStatus('');
    setResponseCode(null);
    try {
      const response = await fetch(url.customers.findById_Put_Delete, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(values),
      });
      const data = await response.json();

      setResponseCode(response.status);

      if (response.ok) {
        setStatus('Customer updated successfully!');
      } else {
        setStatus(`Error: ${data.title || 'Failed to update Customer'}`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to update Customer'}`);
    }
    setSubmitting(false);
    setDialogOpen(true);
  }

  const handleClose = () => {
    setDialogOpen(false);
    if (responseCode === 200) {
      navigate("/customers");
    }
  }

  return (
    <Box m="20px">
      <Header title="EDIT CUSTOMER" subtitle="Edit this Customer" />

      <Formik
        onSubmit={handleFormSubmit}
        initialValues={initialValues}
        validationSchema={customersSchema}
      >
        {({ values, errors, touched, handleBlur, handleChange, handleSubmit, setFieldValue }) => (
          <form onSubmit={handleSubmit}>
            <Box
              display="grid"
              gap="20px"
              gridTemplateColumns="repeat(4, minmax(0, 1fr))"
              sx={{
                "& > div": { gridColumn: isNonMobile ? undefined : "span 4" },
              }}
            >

              <CustomersFormEditInputs rowData={rowData} handleBlur={handleBlur}
                handleChange={handleChange} values={values} touched={touched}
                errors={errors} />

              <FormControl
                variant="filled"
                sx={{ gridColumn: "span 2" }}
              >
                <InputLabel id="employee-select-label">Employee Id</InputLabel>
                <Select
                  labelId="employee-select-label"
                  id="employee-select"
                  name="employeeId"
                  //value={values.employeeId !== null ? rowData.employeeId : ""}
                  value={values.employeeId ?? ""}
                  onChange={(event) => setFieldValue('employeeId', event.target.value)}
                  onBlur={handleBlur}
                  label="Employee Id"
                >
                  <MenuItem value="">
                    <em>None</em>
                  </MenuItem>

                  {/* TODO passar a lista de vendedores */}
                  {employeeIds.map((id) => (
                    <MenuItem key={id} value={id}>
                      {id}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Save
              </Button>
            </Box>

            <Dialog open={dialogOpen} onClose={handleClose}>
              <DialogTitle>Operation Status</DialogTitle>
              <DialogContent>
                <DialogContentText>
                  {status}
                  {responseCode !== null && <br />}
                  Response Code: {responseCode}
                </DialogContentText>
                <DialogActions>
                  <Button onClick={handleClose} color="inherit">
                    OK
                  </Button>
                </DialogActions>
              </DialogContent>
            </Dialog>
          </form>
        )}
      </Formik>
    </Box>
  );
}

export default FormEditCustomer;
