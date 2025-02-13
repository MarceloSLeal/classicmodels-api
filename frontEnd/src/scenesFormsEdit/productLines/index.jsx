import { useLocation } from "react-router-dom";
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

const MAX_FILE_SIZE = 200 * 1024;

const validFileExtension = { image: ['png', 'jpeg'] };

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
    .nullable()
    .test(
      "is-valid-type",
      "Not a valid image type. Allowed types: png, jpeg",
      (value) => {
        if (!value) return true;
        // Se for uma string, verifique se ela começa com os prefixos conhecidos para jpeg ou png
        if (typeof value === "string") {
          return value.startsWith("/9j/") || value.startsWith("iVBOR");
        }
        // Se for um File, valide pelo nome
        if (value instanceof File) {
          return isValidFileType(value.name, "image");
        }
        return false;
      }
    )
    .test(
      "is-valid-size",
      `File size must be less than ${MAX_FILE_SIZE / 1024}KB`,
      (value) => {
        if (!value) return true;
        if (value instanceof File) {
          return value.size <= MAX_FILE_SIZE;
        }
        return true;
      }
    ),
});

const FormEditProductLines = () => {
  const location = useLocation();
  const { rowData } = location.state || {};
  const url = Urls();
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const resetImageRef = useRef(null);

  const initialValues = {
    productLine: rowData.productLine,
    textDescription: rowData.textDescription,
    htmlDescription: rowData.htmlDescription || "",
    image: rowData?.image || "",
  };

  const handleFormSubmit = (values) => {
    console.log(values);
  }

  const handleClose = () => {

  }


  return (
    <Box m="20px">
      <Header title="EDIT PRODUCT LINE" subtitle="Edit Product Line" />

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
                  (resetImageRef.current = resetFunc)} isEdit={true}
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
  );

}

export default FormEditProductLines;
