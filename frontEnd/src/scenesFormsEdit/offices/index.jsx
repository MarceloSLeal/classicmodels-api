import { useLocation } from "react-router-dom";
import React, { useState } from "react";

import {
  Box, Button, Dialog, DialogActions, DialogContent, DialogContentText,
  DialogTitle,
  useMediaQuery
} from "@mui/material";

import { Formik } from "formik";
import * as yup from "yup";

import OfficesFormInputs from "../../components/formInputs/Offices";
import { useNavigate } from "react-router-dom";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import Divider from '@mui/material/Divider';

const phoneRegExp = /^((\+[1-9]{1,4}[ -]?)|(\([0-9]{2,3}\)[ -]?)|([0-9]{2,4})[ -]?)*?[0-9]{3,4}[ -]?[0-9]{3,4}$/;

const officesSchema = yup.object().shape({
  city: yup.string().max(50).required(),
  country: yup.string().max(50).required(),
  state: yup.string().max(50),
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
      const response = await fetch(url.offices.findById_Put_Delete, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(values),
      });
      const data = await response.json();

      setResponseCode(response.status);

      if (response.ok) {
        setStatus('Office updated successfully!');
      } else {
        setStatus(`Error: ${data.title || 'Failed to update Office'}`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to update Office'}`);
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
  )

}

export default FormEditOffices;
