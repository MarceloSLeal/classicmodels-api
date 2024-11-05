import React, { useState, useRef } from "react";
import { Box, Typography, Button } from "@mui/material";

import { TextField, FormControl, InputLabel, Select, MenuItem, FormHelperText } from "@mui/material";
import Divider from '@mui/material/Divider';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import { CloudUpload } from "@mui/icons-material";

const ProductLinesFormInput = ({ handleBlur, handleChange, values, touched,
  errors, productLinesSchema, setFieldValue, }) => {

  const cloud = CloudUpload;
  const [image, setImage] = useState(null);
  const [dragActive, setDragActive] = useState(false);
  const inputRef = useRef(null);

  // const handleFileChange = (event) => {
  //   const file = event.target.files[0];
  //   if (file) {
  //     const reader = new FileReader();
  //     reader.onload = () => setImage(reader.result);
  //     reader.readAsDataURL(file);
  //   }
  // };

  // TODO -- fazer a validação para os outros eventos
  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      if (file.size > 200 * 1024) {
        alert("A imagem deve ter menos de 200 KB.");
        return;
      }
      const reader = new FileReader();
      reader.onload = () => setImage(reader.result);
      reader.readAsDataURL(file);
    }
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
    if (file) {
      const reader = new FileReader();
      reader.onload = () => setImage(reader.result);
      reader.readAsDataURL(file);
    }
  };

  const handleClick = () => {
    inputRef.current.click();
  };

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
        name="productLines"
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

      {/* TODO -- falta colocar o componente de imagem */}
      <Box>
        <Box
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
            backgroundSize: 'cover',
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
          accept="image/*"
          ref={inputRef}
          onChange={handleFileChange}
          style={{ display: 'none' }}
        />
      </Box>

    </>
  )

}

export default ProductLinesFormInput;
