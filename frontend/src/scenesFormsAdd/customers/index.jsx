import React, { useState, useEffect } from "react";

import { Box, Button } from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";
import Divider from '@mui/material/Divider';

import { Formik } from "formik";
import * as yup from "yup";

import CustomersFormInputs from "../../components/formInputs/Customers";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import FormListCalls from "../../components/FormsListCalls";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog";
import usePostForms from "../../components/formsRequests/PostForms";

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
  const { fetchPost } = usePostForms();

  const [dataEmployeeIdNameList, setDataEmployeeIdNameList] = useState(null);

  useEffect(() => {
    FormListCalls(url.employees.findByEmployeesIds, setDataEmployeeIdNameList);
  }, []);


  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [resetFormFn, setResetFormFn] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');

  const handleFormSubmit = async (values, { setSubmitting, resetForm }) => {
    setStatus('');
    setResponseCode(null);
    try {

      const response = await fetchPost(values, url.customers.findAll_Post);

      if (response.ok === false) {
        setStatus(`Error: ${response.status} - ${response.statusText}`);
        setDialogOpen(true);
        return;
      }

      setResponseCode(response.status);

      if (response.status === 201) {
        setStatus('Customer created successfully!');
        setResetFormFn(() => resetForm);
      } else {
        setStatus(`Error: ${response.status || 'Failed to create Customer'} - ${response.statusText || ''}`);
      }

    } catch (error) {
      setStatus(`Error: ${error || 'Failed to create Customer'}`);
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

          </form>
        )}
      </Formik>

      <OperationStatusDialog
        dialogOpen={dialogOpen} onClose={handleClose} status={status}
        responseCode={responseCode} onClick={handleClose}
      />

    </Box>
  );
};

export default FormAddCustomer;
