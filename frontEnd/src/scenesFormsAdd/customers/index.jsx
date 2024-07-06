import React from "react";
import { Box, Button, TextField, Select, MenuItem, FormControl, InputLabel, Menu } from "@mui/material";
import { Formik } from "formik";
import * as yup from "yup";
import useMediaQuery from "@mui/material/useMediaQuery";
import Header from "../../components/Header";
import { useLocation } from "react-router-dom";

const initialValues = {
  name: "", email: "", contactLastName: "", contactFirstName: "", phone: "",
  addressLine1: "", addressLine2: "", city: "", state: "", postalCode: "",
  country: "", creditLimit: "", employeeId: "",
};

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
  creditLimit: yup.number().positive().max(10000.0).required(),
  employeeId: yup.number().positive(),
});

const FormAddCustomer = () => {
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const location = useLocation();
  const { data } = location.state || {};

  const employeeIds = [...new Set(data.map(item => item.employeeId).filter(id => id !== null))];

  const handleFormSubmit = (values) => {
    //--TODO enviar o formulário para o endpoint POST de customers
    console.log(values);
  };

  return (
    <Box m="20px">
      <Header title="CREATE CUSTOMER" subtitle="Create a new Customer" />

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
              <TextField
                variant="filled"
                type="text"
                label="Name"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.name}
                name="name"
                error={!!touched.name && !!errors.name}
                helperText={touched.name && errors.name}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
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
              <TextField
                variant="filled"
                type="text"
                label="Contact Last Name"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.contactLastName}
                name="contactLastName"
                error={!!touched.contactLastName && !!errors.contactLastName}
                helperText={touched.contactLastName && errors.contactLastName}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
                variant="filled"
                type="text"
                label="Contact First Name"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.contactFirstName}
                name="contactFirstName"
                error={!!touched.contactFirstName && !!errors.contactFirstName}
                helperText={touched.contactFirstName && errors.contactFirstName}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
                variant="filled"
                type="text"
                label="Phone"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.phone}
                name="phone"
                error={!!touched.phone && !!errors.phone}
                helperText={touched.phone && errors.phone}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
                variant="filled"
                type="text"
                label="Address Line 1"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.addressLine1}
                name="addressLine1"
                error={!!touched.addressLine1 && !!errors.addressLine1}
                helperText={touched.addressLine1 && errors.addressLine1}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
                variant="filled"
                type="text"
                label="Address Line 2"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.addressLine2}
                name="addressLine2"
                error={!!touched.addressLine2 && !!errors.addressLine2}
                helperText={touched.addressLine2 && errors.addressLine2}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
                variant="filled"
                type="text"
                label="City"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.city}
                name="city"
                error={!!touched.city && !!errors.city}
                helperText={touched.city && errors.city}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
                variant="filled"
                type="text"
                label="State"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.state}
                name="state"
                error={!!touched.state && !!errors.state}
                helperText={touched.state && errors.state}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
                variant="filled"
                type="text"
                label="Postal Code"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.postalCode}
                name="postalCode"
                error={!!touched.postalCode && !!errors.postalCode}
                helperText={touched.postalCode && errors.postalCode}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
                variant="filled"
                type="text"
                label="Country"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.country}
                name="country"
                error={!!touched.country && !!errors.country}
                helperText={touched.country && errors.country}
                sx={{ gridColumn: "span 2" }}
              />
              <TextField
                variant="filled"
                type="text"
                label="Credit Limit"
                onBlur={handleBlur}
                onChange={handleChange}
                value={values.creditLimit}
                name="creditLimit"
                error={!!touched.creditLimit && !!errors.creditLimit}
                helperText={touched.creditLimit && errors.creditLimit}
                sx={{ gridColumn: "span 2" }}
              />
              <FormControl
                variant="filled"
                sx={{ gridColumn: "span 2" }}
              >
                <InputLabel id="employee-select-label">Employee Id</InputLabel>
                <Select
                  labelId="employee-select-label"
                  id="employee-select"
                  name="employeeId"
                  value={values.employeeId}
                  onChange={(event) => setFieldValue('employeeId', event.target.value)}
                  onBlur={handleBlur}
                  label="Employee Id"
                >
                  <MenuItem value="">
                    <em>None</em>
                  </MenuItem>
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
                Create New Customer
              </Button>
            </Box>
          </form>
        )}
      </Formik>
    </Box>
  );
};

export default FormAddCustomer;
