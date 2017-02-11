package com.tenma.common.util.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ndwijaya on 4/18/2016.
 */
public class TenmaS3Util {
    private static TenmaS3Util instance;
    private AmazonS3 s3client;

    public TenmaS3Util() {
        ClientConfiguration config = new ClientConfiguration();
        config.setProtocol(Protocol.HTTP);
        config.setSignerOverride("S3SignerType");
        //TODO-set credential spesific for every community.
        AWSCredentials awsCredentials = new BasicAWSCredentials("40c02223e321b1741df9", "amjqeBKocqaS8XNGcVc6gD6ruxnVetHxs1Xiswdo");
        s3client = new AmazonS3Client(awsCredentials, config);
        s3client.setEndpoint("kilatstorage.share");
    }

    public static TenmaS3Util getInstance() {
        if (instance == null) {
            instance = new TenmaS3Util();
        }
        return instance;
    }

    public static void main(String[] args) {
        TenmaS3Util.getInstance().listBucket();
    }

    public Bucket createBucket(String bucketName) {
        CreateBucketRequest bucketRequest = new CreateBucketRequest(bucketName);
        Bucket bucket = s3client.createBucket(bucketRequest);
        return bucket;
    }

    public PutObjectResult addObjectToBucket(String bucketName, String key, File file) {
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        PutObjectRequest pr = new PutObjectRequest("bucketName", key, file).withAccessControlList(acl);
        PutObjectResult rs = s3client.putObject(pr);
        return rs;
    }

    public PutObjectResult addObjectToBucket(String bucketName, String key, InputStream inputStream, ObjectMetadata metadata) {
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        PutObjectRequest pr = new PutObjectRequest("bucketName", key, inputStream, metadata).withAccessControlList(acl);
        PutObjectResult rs = s3client.putObject(pr);
        return rs;
    }

    public List<TenmaS3DocModel> listDocOnBucket(String bucketName) {
        List<TenmaS3DocModel> ls = new ArrayList<>();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix("");
        ObjectListing objectListing;

        do {
            objectListing = s3client.listObjects(listObjectsRequest);
            for (S3ObjectSummary objectSummary :
                    objectListing.getObjectSummaries()) {
                TenmaS3DocModel m = new TenmaS3DocModel();
                System.out.println(" - " + objectSummary.getKey() + "  " +
                        "(size = " + objectSummary.getSize() +
                        ")");
                m.setDocName(objectSummary.getKey());
                m.setDocSize(objectSummary.getSize());
                m.setLastModify(objectSummary.getLastModified());
                m.setIsBucket(false);
                ls.add(m);
            }
            listObjectsRequest.setMarker(objectListing.getNextMarker());
        } while (objectListing.isTruncated());
        return ls;
    }

    public List<TenmaS3DocModel> listBucket() {
        List<TenmaS3DocModel> ls = new ArrayList<>();

        List<Bucket> lsBucket = s3client.listBuckets();
        ;
        for (Bucket bucket : lsBucket) {
            TenmaS3DocModel m = new TenmaS3DocModel();
            System.out.println("(size = " + bucket.getName() +
                    ")");
            m.setDocName(bucket.getName());
            m.setDocSize(0);
            m.setLastModify(bucket.getCreationDate());
            m.setIsBucket(true);
            ls.add(m);
        }
        return ls;
    }


}
