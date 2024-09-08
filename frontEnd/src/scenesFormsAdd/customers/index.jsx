import React, { useState } from "react";

import {
  Box, Button, Dialog, DialogActions,
  DialogContent, DialogContentText, DialogTitle,
} from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";

import { Formik } from "formik";
import * as yup from "yup";

import CustomersFormInputs from "../../components/formInputs/Customers";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import FormListCalls from "../../components/FormsListCalls";
import Divider from '@mui/material/Divider';

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
    .max(50)
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

const FormAddCustomer = () => {
  const url = Urls();

  const [dataEmployeeIdNameList, setDataEmployeeIdNameList] = useState(null);
  FormListCalls(url.employees.findByEmployeesIds, setDataEmployeeIdNameList);

  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [resetFormFn, setResetFormFn] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');

  const handleFormSubmit = async (values, { setSubmitting, resetForm }) => {
    setStatus('');
    setResponseCode(null);
    try {
      const response = await fetch(url.customers.findAll_Post, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(values),
      });
      const data = await response.json();

      setResponseCode(response.status);

      if (response.ok) {
        setStatus('Customer created successfully!');
        setResetFormFn(() => resetForm);
      } else {
        setStatus(`Error: ${data.title || 'Failed to create Customer'}`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to create Customer'}`);
    }
    setSubmitting(false);
    setDialogOpen(true);
  }

  const handleClose = () => {
    setDialogOpen(false);
    if (responseCode === 201 && resetFormFn) {
      resetFormFn();
    }
  }

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
              gridTemplateColumns="repeat(5, minmax(0, 1fr))"
              sx={{
                "& > div": { gridColumn: isNonMobile ? undefined : "span 5" },
              }}
            >

              <Divider sx={{ gridColumn: "span 5" }} />

              <CustomersFormInputs handleBlur={handleBlur} handleChange={handleChange}
                values={values} touched={touched} errors={errors} isEdit={false}
                dataEmployeeIdNameList={dataEmployeeIdNameList} setFieldValue={setFieldValue} />

            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Create New Customer
              </Button>
            </Box>

            <Divider sx={{ gridColumn: "span 5" }} />

            <Dialog open={dialogOpen} onClose={handleClose}>
              <DialogTitle>Operation Status</DialogTitle>
              <DialogContent>
                <DialogContentText>
                  {status}
                  {responseCode !== null && <br />}
                  Response Code: {responseCode}
                </DialogContentText>
                <DialogActions>
                  <Button onClick={handleClose} color="primary">
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
};

export default FormAddCustomer;
