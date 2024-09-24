import { useLocation } from "react-router-dom";
import React, { useState } from "react";
import dayjs from 'dayjs';

import { Box, Button, useMediaQuery } from "@mui/material";
import Divider from '@mui/material/Divider';

import { Formik } from "formik";
import * as yup from "yup";

import OrdersFormInput from "../../components/formInputs/Orders";
import { useNavigate } from "react-router-dom";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog"
import { Constants } from "../../data/constants";

const commentsRegex = /^[\p{L}\p{N}\s.,!?'"()-]+$/u;
const ordersSchema = yup.object().shape({
  id: yup.number().required(),
  date: yup.date().required(),
  shippedDate: yup.date().nullable(),
  requiredDate: yup.date(),
  status: yup.string().required(),
  comments: yup.string().matches(commentsRegex, "Accepts only text"),
  customerId: yup.number().required(),
});

// TODO -- verificar a geração do shippedDate no POST de ORDERS
const FormEditOrders = () => {
  const location = useLocation();
  const { rowData } = location.state || {};
  const url = Urls(rowData.id);
  const orderStatus = Constants().orders.status;

  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const navigate = useNavigate();

  const initialValues = {
    id: rowData.id,
    date: dayjs(rowData.date),
    shippedDate: rowData.shippedDate ? dayjs(rowData.shippedDate) : null,
    requiredDate: dayjs(rowData.requiredDate),
    status: rowData.status,
    comments: rowData.comments,
    customerId: rowData.customerId,
  }

  const handleFormSubmit = async (submitValues, { setSubmitting }) => {

    console.log("body", submitValues);

    setStatus('');
    setResponseCode(null);
    try {
      const response = await fetch(url.orders.findById_Put_Delete, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(submitValues),
      });
      const data = await response.json();

      setResponseCode(response.status);

      if (response.ok) {
        setStatus('Order updated successfully!');
      } else {
        setStatus(`Error: ${data.title || 'Failed to update Order'}`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to update Order'}`);
    }
    setSubmitting(false);
    setDialogOpen(true);
  }

  const handleClose = () => {
    setDialogOpen(false);
    if (responseCode === 200) {
      navigate("/orders");
    }
  }

  return (
    <Box m="20px">
      <Header title="EDIT ORDER" subtitle="Edit this Order" />

      <Formik
        initialValues={initialValues}
        validationSchema={ordersSchema}
        onSubmit={(values, formikHelpers) => {
          const submitValues = {
            shippedDate: values.shippedDate ? values.shippedDate.format('YYYY-MM-DDTHH:mm:ssZ') : null,
            status: values.status,
            comments: values.comments,
          };
          handleFormSubmit(submitValues, formikHelpers);
        }}
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

              <OrdersFormInput
                handleBlur={handleBlur} handleChange={handleChange} values={values}
                touched={touched} errors={errors} ordersSchema={ordersSchema}
                setFieldValue={setFieldValue} orderStatus={orderStatus}
              />


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

export default FormEditOrders;
