import React, { useState, useEffect } from "react";

import { Box, Button } from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";
import Divider from '@mui/material/Divider';

import { Formik } from "formik";
import * as yup from "yup";

import ProductsFormInputs from "../../components/formInputs/ProductsFormInput";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import FormListCalls from "../../components/FormsListCalls";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog";
import usePostForms from "../../components/formsRequests/PostForms";

const initialValues = {
  name: "", productLine: "", scale: "", vendor: "", description: "",
  quantityInStock: "", buyPrice: "", msrp: ""
}

const productsSchema = yup.object().shape({
  name: yup.string().max(70).required(),
  productLine: yup.string().max(50).required(),
  scale: yup.string().max(10).required(),
  vendor: yup.string().max(50).required(),
  description: yup.string().max(4000).required(),
  quantityInStock: yup.number().required(),
  buyPrice: yup.number().required(),
  msrp: yup.number().required()
});

const FormAddProducts = () => {
  const url = Urls();

  const [dataProductLine, setDataProductLine] = useState(null);

  useEffect(() => {
    FormListCalls(url.productlines.findByProductLineList, setDataProductLine);
  }, []);

  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [resetFormFn, setResetFormFn] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const { fetchPost } = usePostForms();

  const handleFormSubmit = async (values, { setSubmitting, resetForm }) => {
    setStatus('');
    setResponseCode(null);
    try {

      const response = await fetchPost(values, url.products.findAll_Post);

      if (response.ok === false) {
        setStatus(`Error: ${response.status} - ${response.message}`);
        setDialogOpen(true);
        return;
      }

      setResponseCode(response.status);

      if (response.status === 201) {
        setStatus('Product created successfully!');
        setResetFormFn(() => resetForm);
      } else {
        setStatus(`Error: ${respone.title || 'Failed to create Product'} - ${response.detail || ''}`);
      }
    } catch (error) {
      setStatus(`Error: ${error || 'Failed to create Product'}`);
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
      <Header title="CREATE PRODUCT" subtitle="Create a new Product" />

      <Formik
        onSubmit={handleFormSubmit}
        initialValues={initialValues}
        validationSchema={productsSchema}
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

              <ProductsFormInputs handleBlur={handleBlur} handleChange={handleChange}
                values={values} touched={touched} errors={errors} isEdit={false}
                setFieldValue={setFieldValue} dataProductLine={dataProductLine} />

            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Create New Product
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
}

export default FormAddProducts;
