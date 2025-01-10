import React, { useState, useRef } from "react";
import { Box, Typography, Button } from "@mui/material";

import { TextField, FormControl, InputLabel, Select, MenuItem, FormHelperText } from "@mui/material";
import Divider from '@mui/material/Divider';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';

const ProductLinesFormInput = ({ handleBlur, handleChange, values, touched,
  errors, productLinesSchema, setFieldValue, }) => {

  const [image, setImage] = useState(null);
  const [dragActive, setDragActive] = useState(false);
  const inputRef = useRef(null);


  // const validateAndLoadFile = (file) => {
  //   if (!file) return;
  //
  //   // if (file.size > 200 * 1024) {
  //   //   alert("The file must be less than 200Kb");
  //   //   return;
  //   // }
  //
  //   const reader = new FileReader();
  //   reader.onload = () => {
  //     const result = reader.result;
  //     setImage(result);
  //     setFieldValue('image', result);
  //     values.image = image;
  //   };
  //   reader.readAsDataURL(file);
  // };

  const validateAndLoadFile = (file) => {
    if (!file) return;

    // Atualiza a imagem para pré-visualização
    const reader = new FileReader();
    reader.onload = () => {
      setImage(reader.result); // Define o base64 apenas para exibição
    };
    reader.readAsDataURL(file);

    // Define o arquivo original no Formik
    setFieldValue("image", file);
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    validateAndLoadFile(file);
  };

  const handleDragOver = (event) => {
    event.preventDefault();
    setDragActive(true);
  };

  const handleDragLeave = () => {
    setDragActive(false);
  };

  const handleDrop = (event) => {
    event.preventDefault();
    setDragActive(false);
    const file = event.dataTransfer.files[0];
    validateAndLoadFile(file);
  };

  const handleClick = () => {
    inputRef.current.click();
  };

  const validFileExtensions = ['jpg', 'png', 'jpeg'];

  return (
    <>
      <Divider sx={{ gridColumn: "span 5" }} />

      <TextField
        variant="filled"
        type="text"
        label="Product Line"
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.productLine}
        name="productLine"
        error={!!touched.productLine && !!errors.productLine}
        helperText={touched.productLine && errors.productLine}
        sx={{ gridColumn: "span 1" }}
      />

      <TextField
        variant="filled"
        type="text"
        label="Text Description"
        multiline
        rows={5}
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.textDescription}
        name="textDescription"
        error={!!touched.textDescription && !!errors.textDescription}
        helperText={touched.textDescription && errors.textDescription}
        sx={{ gridColumn: "span 1" }}
      />

      <TextField
        variant="filled"
        type="text"
        label="Html Description"
        multiline
        rows={5}
        onBlur={handleBlur}
        onChange={handleChange}
        value={values.htmlDescription}
        name="htmlDescription"
        error={!!touched.htmlDescription && !!errors.htmlDescription}
        helperText={touched.htmlDescription && errors.htmlDescription}
        sx={{ gridColumn: "span 1" }}
      />

      <Box>
        <Box
          role="button"
          aria-label="Upload an image"
          onDragOver={handleDragOver}
          onDragLeave={handleDragLeave}
          onDrop={handleDrop}
          onClick={handleClick}
          sx={{
            gridColumn: "span 2",
            width: 600,
            height: 400,
            border: '2px dashed #aaa',
            borderRadius: '8px',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            cursor: 'pointer',
            backgroundColor: dragActive ? '#1f2a40' : 'transparent',
            backgroundImage: image ? `url(${image})` : 'none',
            backgroundSize: 'contain',
            backgroundPosition: 'center',
            backgroundRepeat: 'no-repeat',
            position: 'relative',
            overflow: 'hidden',
          }}
        >
          {!image && (
            <>
              <CloudUploadIcon
                sx={{
                  fontSize: 80,
                  color: '#aaa',
                  mb: 1,
                }}
              />
              <Typography color="textSecondary">
                {dragActive ? 'Drop image here' : 'Drag one image or click to select'}
              </Typography>
            </>
          )}
        </Box>
        <input
          type="file"
          name="image"
          accpet={validFileExtensions.map(ext => `image/${ext}`).join(',')}
          ref={inputRef}
          // onChange={(event) => {
          //   setFieldValue("image", event.currentTarget.files[0]);
          //
          //   handleFileChange(event)
          // }}
          onChange={(event) => {
            const file = event.currentTarget.files[0];
            validateAndLoadFile(file); // Valida e define o arquivo
          }}
          style={{ display: 'none' }}
        />
        <div>
          {touched.image && errors.image && (
            <p style={{ color: 'red' }}>{errors.image}</p>
          )}
        </div>
      </Box>

    </>
  )

}

export default ProductLinesFormInput;
