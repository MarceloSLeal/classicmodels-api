import React, { useState, useRef } from "react";

import { Box, Button } from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";
import Divider from '@mui/material/Divider';

import { Formik } from "formik";
import * as yup from "yup";

import ProductLinesFormInput from "../../components/formInputs/ProductLines";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog"

const initialValues = {
  productLine: "",
  textDescription: "",
  htmlDescription: "",
  image: "",
};

const MAX_FILE_SIZE = 200 * 1024;

const validFileExtension = { image: ['jpg', 'png', 'jpeg'] };

function isValidFileType(fileName, fileType) {
  if (!fileName) return false;
  const extension = fileName.split('.').pop().toLowerCase();
  return validFileExtension[fileType]?.includes(extension);
}

const productLinesSchema = yup.object().shape({
  productLine: yup.string().max(50).required(),
  textDescription: yup.string().max(4000),
  htmlDescription: yup.string(),
  image: yup
    .mixed()
    .test(
      "is-valid-type",
      "Not a valid image type. Allowed types: jpg, png, jpeg",
      value => {
        return value ? isValidFileType(value.name, "image") : false;
      }
    )
    .test(
      "is-valid-size",
      `File size must be less than ${MAX_FILE_SIZE / 1024}KB`,
      (value) => {
        return value ? value.size <= MAX_FILE_SIZE : false;
      }
    ),
});

const FormAddProductLines = () => {
  const url = Urls();
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const resetImageRef = useRef(null);

  const handleFormSubmit = async (values, { setSubmitting, resetForm }) => {
    setStatus('');
    setResponseCode(null);

    const formData = new FormData();

    Object.keys(values).forEach((key) => {
      if (key === "image" && values[key] instanceof File) {
        formData.append(key, values[key]);
      } else {
        formData.append(key, values[key]);
      }
    });

    try {
      const response = await fetch(url.productlines.findAll_Post, {
        method: 'POST',
        credentials: "include",

        body: formData,
      });
      const data = await response.json();

      setResponseCode(response.status);

      if (response.ok) {
        setStatus('Product Line created successfully!');
        resetForm();
        if (resetImageRef.current) resetImageRef.current();
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

              <ProductLinesFormInput
                handleBlur={handleBlur} handleChange={handleChange} values={values} touched={touched}
                errors={errors} setFieldValue={setFieldValue} onResetImage={(resetFunc) =>
                  (resetImageRef.current = resetFunc)}
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
