import React, { useState } from "react";

import { Box, Button } from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";
import Divider from '@mui/material/Divider';

import { Formik } from "formik";
import * as yup from "yup";

import ProductLinesFormInput from "../../components/formInputs/ProductLinesInput";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import FormListCalls from "../../components/FormsListCalls";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog"

const initialValues = {
  productLine: "",
  textDescription: "",
  htmlDescription: "",
  image: null,
};

const productLinesSchema = yup.object().shape({
  productLine: yup.string().max(50).required(),
  textDescription: yup.string().max(4000),
  htmlDescription: yup.string(),
  image: yup
    .mixed()
    .test("fileSize", "The file size shold be less than 2MB", (file) => {
      return file && file.size <= 2 * 1024 * 1024;
    })
    .test("fileFormat", "Only .jpg files are allowed", (file) => {
      return file && file.type === "image/jpeg";
    }),
});

const FormAddProductLines = () => {
  const url = Urls();
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [resetFormFn, setResetFormFn] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');

  const handleFormSubmit = async (values, { setSubmitting, resetForm }) => {
    setStatus('');
    setResponseCode(null);
    try {
      // TODO -- mudar o caminho do submit
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
        setStatus('Product Line created successfully!');
        setResetFormFn(() => resetForm);
      } else {
        setStatus(`Error: ${data.title || 'Failed to create Product Line'} - ${data.detail || ''}`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to create Product Line'}`);
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
      <Header title="CREATE PRODUCT LINE" subtitle="Create a new Product Line" />

      <Formik
        onSubmit={handleFormSubmit}
        initialValues={initialValues}
        validationSchema={productLinesSchema}
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

              {/* TODO -- criar o ProductLinesFormInput */}
              {/* <CustomersFormInputs handleBlur={handleBlur} handleChange={handleChange} */}
              {/*   values={values} touched={touched} errors={errors} isEdit={false} */}
              {/*   dataEmployeeIdNameList={dataEmployeeIdNameList} setFieldValue={setFieldValue} /> */}

              <ProductLinesFormInput
                handleBlur={handleBlur} handleChange={handleChange} values={values} touched={touched}
                errors={errors} productLinesSchema={productLinesSchema} setFieldValue={setFieldValue}
              />


            </Box>
            <Box display="flex" justifyContent="end" mt="20px">
              <Button type="submit" color="secondary" variant="contained">
                Create New Product Line
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

export default FormAddProductLines;
