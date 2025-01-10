import React, { useState } from "react";

import { Box, Button } from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";
import Divider from '@mui/material/Divider';

import { Formik } from "formik";
import * as yup from "yup";

import ProductLinesFormInput from "../../components/formInputs/ProductLines";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import FormListCalls from "../../components/FormsListCalls";
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
  if (!fileName) return false; // Verifica se o nome do arquivo é válido
  const extension = fileName.split('.').pop().toLowerCase(); // Obtém a extensão do arquivo
  return validFileExtension[fileType]?.includes(extension); // Verifica se a extensão é permitida
}

const productLinesSchema = yup.object().shape({
  productLine: yup.string().max(50).required(),
  textDescription: yup.string().max(4000),
  htmlDescription: yup.string(),
  // image: yup
  //   .mixed()
  //   .test("fileSize", "The file size shold be less than 2MB", (file) => {
  //     return file && file.size <= 200 * 1024;
  //   })
  //   .test("fileFormat", "Only .jpg files are allowed", (file) => {
  //     return file && file.type === "image/jpg";
  //   }),
  image: yup
    .mixed()
    .test(
      "is-valid-type",
      "Not a valid image type. Allowed types: jpg, png, jpeg",
      value => {
        // Garante que o valor não é nulo antes de validar o tipo
        return value ? isValidFileType(value.name, "image") : false;
      }
    )
    .test(
      "is-valid-size",
      `File size must be less than ${MAX_FILE_SIZE / 1024}KB`,
      (value) => {
        // Garante que o valor não é nulo antes de validar o tamanho
        return value ? value.size <= MAX_FILE_SIZE : false;
      }
    ),
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

    const formData = new FormData();

    Object.keys(values).forEach((key) => {
      if (key === "image" && values[key] instanceof File) {
        formData.append(key, values[key]); // Certifique-se de que values[key] seja um objeto File
      } else {
        formData.append(key, values[key]);
      }
    });

    // const formData = new FormData();
    // formData.append("productLine", values.productLine);
    // formData.append("textDescription", values.textDescription);
    // formData.append("htmlDescription", values.htmlDescription);
    // formData.append("image", values.image); // Certifique-se de que values.image seja um File/Blob.


    for (let pair of formData.entries()) {
      console.log(`${pair[0]}: ${pair[1]}`);
    }

    try {

      const response = await fetch(url.productlines.findAll_Post, {
        method: 'POST',
        credentials: "include",

        body: formData,
      });
      const data = await response.json();

      setResponseCode(response.status);

      if (response.ok) {
        console.log("Requisição bem sucedida");
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
