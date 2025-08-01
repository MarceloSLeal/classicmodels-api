import { useLocation } from "react-router-dom";
import React, { useState } from "react";

import { Box, Button, useMediaQuery } from "@mui/material";
import Divider from '@mui/material/Divider';

import { Formik } from "formik";
import * as yup from "yup";

import OfficesFormInputs from "../../components/formInputs/Offices";
import { useNavigate } from "react-router-dom";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog"
import usePutForms from "../../components/formsRequests/PutForms";

const phoneRegExp = /^((\+[1-9]{1,4}[ -]?)|(\([0-9]{2,3}\)[ -]?)|([0-9]{2,4})[ -]?)*?[0-9]{3,4}[ -]?[0-9]{3,4}$/;

const officesSchema = yup.object().shape({
  city: yup.string().max(50).required(),
  country: yup.string().max(50).required(),
  state: yup.string().max(50).nullable(),
  phone: yup
    .string()
    .matches(phoneRegExp, "Phone number is not valid")
    .max(50)
    .required(),
  addressLine1: yup.string().max(50).required(),
  addressLine2: yup.string().max(50),
  postalCode: yup.string().max(15).required(),
  territory: yup.string().max(10).required(),
})

const FormEditOffices = () => {
  const location = useLocation();
  const { rowData } = location.state || {};
  const url = Urls(rowData.id);

  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const navigate = useNavigate();
  const { fetchPut } = usePutForms();

  const initialValues = {
    id: rowData.id, city: rowData.city, country: rowData.country,
    state: rowData.state, phone: rowData.phone, addressLine1: rowData.addressLine1,
    addressLine2: rowData.addressLine2, postalCode: rowData.postalCode,
    territory: rowData.territory
  }

  const handleFormSubmit = async (values, { setSubmitting }) => {
    setStatus('');
    setResponseCode(null);
    try {

      const response = await fetchPut(values, url.offices.findById_Put_Delete);

      if (response.ok == false ) {
        setStatus(`Error: ${response.status} - ${response.statusText}`);
        setDialogOpen(true);
        return;
      }

      setResponseCode(response.status);

      if (response.ok) {
        setStatus('Office updated successfully!');
      } else {
        setStatus(`Error: ${response.status || 'Failed to update Office'} - ${response.statusText || ''}`);
      }
    } catch (error) {
      setStatus(`Error: ${error || 'Failed to update Office'}`);
    }
    setSubmitting(false);
    setDialogOpen(true);
  }

  const handleClose = () => {
    setDialogOpen(false);
    if (responseCode === 200) {
      navigate("/offices");
    }
  }

  return (
    <Box m="20px">
      <Header title="EDIT OFFICE" subtitle="Edit this Office" />

      <Formik
        onSubmit={handleFormSubmit}
        initialValues={initialValues}
        validationSchema={officesSchema}
      >
        {({ values, errors, touched, handleBlur, handleChange, handleSubmit
        }) => (
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


              <OfficesFormInputs
                handleBlur={handleBlur} handleChange={handleChange}
                values={values} touched={touched} errors={errors} isEdit={true} />

            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Save
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
  )

}

export default FormEditOffices;
